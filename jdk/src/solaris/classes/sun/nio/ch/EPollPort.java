/*
 * Copyright (c) 2008, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package sun.nio.ch;

import java.nio.channels.spi.AsynchronousChannelProvider;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import static sun.nio.ch.EPoll.*;

/**
 * AsynchronousChannelGroup implementation based on the Linux epoll facility.
 */

final class EPollPort
    extends Port
{
    // maximum number of events to poll at a time
    private static final int MAX_EPOLL_EVENTS = 512;

    // errors
    private static final int ENOENT     = 2;

    // epoll file descriptor
    private final int epfd;

    // true if epoll closed
    private boolean closed;

    // socket pair used for wakeup
    private final int sp[];

    // number of wakeups pending
    private final AtomicInteger wakeupCount = new AtomicInteger();

    // address of the poll array passed to epoll_wait
    private final long address;

    // encapsulates an event for a channel
    static class Event {
        final PollableChannel channel;
        final int events;

        Event(PollableChannel channel, int events) {
            this.channel = channel;
            this.events = events;
        }

        PollableChannel channel()   { return channel; }
        int events()                { return events; }
    }

    // queue of events for cases that a polling thread dequeues more than one
    // event
    private final ArrayBlockingQueue<Event> queue;
    private final Event NEED_TO_POLL = new Event(null, 0);
    private final Event EXECUTE_TASK_OR_SHUTDOWN = new Event(null, 0);

    EPollPort(AsynchronousChannelProvider provider, ThreadPool pool)
        throws IOException
    {
        super(provider, pool);

        // open epoll
        this.epfd = epollCreate();

        // create socket pair for wakeup mechanism
        int[] sv = new int[2];
        try {
            socketpair(sv);
            // register one end with epoll
            epollCtl(epfd, EPOLL_CTL_ADD, sv[0], POLLIN);
        } catch (IOException x) {
            close0(epfd);
            throw x;
        }
        this.sp = sv;

        // allocate the poll array
        this.address = allocatePollArray(MAX_EPOLL_EVENTS);

        // create the queue and offer the special event to ensure that the first
        // threads polls
        this.queue = new ArrayBlockingQueue<Event>(MAX_EPOLL_EVENTS);
        this.queue.offer(NEED_TO_POLL);
    }

    EPollPort start() {
        startThreads(new EventHandlerTask());
        return this;
    }

    /**
     * Release all resources
     */
    private void implClose() {
        synchronized (this) {
            if (closed)
                return;
            closed = true;
        }
        freePollArray(address);
        close0(sp[0]);
        close0(sp[1]);
        close0(epfd);
    }

    private void wakeup() {
        if (wakeupCount.incrementAndGet() == 1) {
            // write byte to socketpair to force wakeup
            try {
                interrupt(sp[1]);
            } catch (IOException x) {
                throw new AssertionError(x);
            }
        }
    }

    @Override
    void executeOnHandlerTask(Runnable task) {
        synchronized (this) {
            if (closed)
                throw new RejectedExecutionException();
            offerTask(task);
            wakeup();
        }
    }

    @Override
    void shutdownHandlerTasks() {
        /*
         * If no tasks are running then just release resources; otherwise
         * write to the one end of the socketpair to wakeup any polling threads.
         */
        int nThreads = threadCount();
        if (nThreads == 0) {
            implClose();
        } else {
            // send interrupt to each thread
            while (nThreads-- > 0) {
                wakeup();
            }
        }
    }

    // invoke by clients to register a file descriptor
    @Override
    void startPoll(int fd, int events) {
        // update events (or add to epoll on first usage)
        int err = epollCtl(epfd, EPOLL_CTL_MOD, fd, (events | EPOLLONESHOT));
        if (err == ENOENT)
            err = epollCtl(epfd, EPOLL_CTL_ADD, fd, (events | EPOLLONESHOT));
        if (err != 0)
            throw new AssertionError();     // should not happen
    }

    /*
     * Task to process events from epoll and dispatch to the channel's
     * onEvent handler.
     *
     * Events are retreived from epoll in batch and offered to a BlockingQueue
     * where they are consumed by handler threads. A special "NEED_TO_POLL"
     * event is used to signal one consumer to re-poll when all events have
     * been consumed.
     */
    private class EventHandlerTask implements Runnable {
        private Event poll() throws IOException {
            try {
                for (;;) {
                    int n = epollWait(epfd, address, MAX_EPOLL_EVENTS);
                    /*
                     * 'n' events have been read. Here we map them to their
                     * corresponding channel in batch and queue n-1 so that
                     * they can be handled by other handler threads. The last
                     * event is handled by this thread (and so is not queued).
                     */
                    fdToChannelLock.readLock().lock();
                    try {
                        while (n-- > 0) {
                            long eventAddress = getEvent(address, n);
                            int fd = getDescriptor(eventAddress);

                            // wakeup
                            if (fd == sp[0]) {
                                if (wakeupCount.decrementAndGet() == 0) {
                                    // no more wakeups so drain pipe
                                    drain1(sp[0]);
                                }

                                // queue special event if there are more events
                                // to handle.
                                if (n > 0) {
                                    queue.offer(EXECUTE_TASK_OR_SHUTDOWN);
                                    continue;
                                }
                                return EXECUTE_TASK_OR_SHUTDOWN;
                            }

                            PollableChannel channel = fdToChannel.get(fd);
                            if (channel != null) {
                                int events = getEvents(eventAddress);
                                Event ev = new Event(channel, events);

                                // n-1 events are queued; This thread handles
                                // the last one except for the wakeup
                                if (n > 0) {
                                    queue.offer(ev);
                                } else {
                                    return ev;
                                }
                            }
                        }
                    } finally {
                        fdToChannelLock.readLock().unlock();
                    }
                }
            } finally {
                // to ensure that some thread will poll when all events have
                // been consumed
                queue.offer(NEED_TO_POLL);
            }
        }

        public void run() {
            Invoker.GroupAndInvokeCount myGroupAndInvokeCount =
                Invoker.getGroupAndInvokeCount();
            final boolean isPooledThread = (myGroupAndInvokeCount != null);
            boolean replaceMe = false;
            Event ev;
            try {
                for (;;) {
                    // reset invoke count
                    if (isPooledThread)
                        myGroupAndInvokeCount.resetInvokeCount();

                    try {
                        replaceMe = false;
                        ev = queue.take();

                        // no events and this thread has been "selected" to
                        // poll for more.
                        if (ev == NEED_TO_POLL) {
                            try {
                                ev = poll();
                            } catch (IOException x) {
                                x.printStackTrace();
                                return;
                            }
                        }
                    } catch (InterruptedException x) {
                        continue;
                    }

                    // handle wakeup to execute task or shutdown
                    if (ev == EXECUTE_TASK_OR_SHUTDOWN) {
                        Runnable task = pollTask();
                        if (task == null) {
                            // shutdown request
                            return;
                        }
                        // run task (may throw error/exception)
                        replaceMe = true;
                        task.run();
                        continue;
                    }

                    // process event
                    try {
                        ev.channel().onEvent(ev.events(), isPooledThread);
                    } catch (Error x) {
                        replaceMe = true; throw x;
                    } catch (RuntimeException x) {
                        replaceMe = true; throw x;
                    }
                }
            } finally {
                // last handler to exit when shutdown releases resources
                int remaining = threadExit(this, replaceMe);
                if (remaining == 0 && isShutdown()) {
                    implClose();
                }
            }
        }
    }

    // -- Native methods --

    private static native void socketpair(int[] sv) throws IOException;

    private static native void interrupt(int fd) throws IOException;

    private static native void drain1(int fd) throws IOException;

    private static native void close0(int fd);

    static {
        IOUtil.load();
    }
}
