/*
 * Copyright (c) 2004, Oracle and/or its affiliates. All rights reserved.
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

package sun.jvmstat.monitor;

/**
 * Base class for exceptions that occur while interfacing with the
 * Monitoring interfaces.
 *
 * @author Brian Doherty
 * @since 1.5
 */
public class MonitorException extends Exception {

    /**
     * Create a MonitorException
     */
    public MonitorException() {
        super();
    }

    /**
     * Create a MonitorException with the given message.
     *
     * @param message the message to associate with the exception.
     */
    public MonitorException(String message) {
        super(message);
    }

    /**
     * Create a MonitorException with the given message and cause.
     *
     * @param message the message to associate with the exception.
     * @param cause the exception causing this exception.
     */
    public MonitorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create an InstrumentationException with the given cause.
     *
     * @param cause the exception causing this exception.
     */
    public MonitorException(Throwable cause) {
        super(cause);
    }
}
