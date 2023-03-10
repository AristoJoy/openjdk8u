/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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

package sun.lwawt.macosx;

import sun.awt.AWTAccessor;
import sun.awt.IconInfo;
import sun.awt.SunToolkit;
import sun.java2d.SunGraphics2D;
import sun.java2d.SurfaceData;
import sun.java2d.opengl.CGLLayer;
import sun.lwawt.LWWindowPeer;
import sun.lwawt.PlatformEventNotifier;
import sun.lwawt.SecurityWarningWindow;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class CWarningWindow extends CPlatformWindow
        implements SecurityWarningWindow, PlatformEventNotifier {

    private static class Lock {};
    private final Lock lock = new Lock();

    private final static int SHOWING_DELAY = 300;
    private final static int HIDING_DELAY = 2000;

    private Rectangle bounds = new Rectangle();
    private final WeakReference<LWWindowPeer> ownerPeer;
    private final Window ownerWindow;

    /**
     * Animation stage.
     */
    private volatile int currentIcon = 0;

    /* -1 - uninitialized.
     * 0 - 16x16
     * 1 - 24x24
     * 2 - 32x32
     * 3 - 48x48
     */
    private int currentSize = -1;
    private static IconInfo[][] icons;
    private static IconInfo getSecurityIconInfo(int size, int num) {
        synchronized (CWarningWindow.class) {
            if (icons == null) {
                icons = new IconInfo[4][3];
                icons[0][0] = new IconInfo(sun.awt.AWTIcon32_security_icon_bw16_png.security_icon_bw16_png);
                icons[0][1] = new IconInfo(sun.awt.AWTIcon32_security_icon_interim16_png.security_icon_interim16_png);
                icons[0][2] = new IconInfo(sun.awt.AWTIcon32_security_icon_yellow16_png.security_icon_yellow16_png);
                icons[1][0] = new IconInfo(sun.awt.AWTIcon32_security_icon_bw24_png.security_icon_bw24_png);
                icons[1][1] = new IconInfo(sun.awt.AWTIcon32_security_icon_interim24_png.security_icon_interim24_png);
                icons[1][2] = new IconInfo(sun.awt.AWTIcon32_security_icon_yellow24_png.security_icon_yellow24_png);
                icons[2][0] = new IconInfo(sun.awt.AWTIcon32_security_icon_bw32_png.security_icon_bw32_png);
                icons[2][1] = new IconInfo(sun.awt.AWTIcon32_security_icon_interim32_png.security_icon_interim32_png);
                icons[2][2] = new IconInfo(sun.awt.AWTIcon32_security_icon_yellow32_png.security_icon_yellow32_png);
                icons[3][0] = new IconInfo(sun.awt.AWTIcon32_security_icon_bw48_png.security_icon_bw48_png);
                icons[3][1] = new IconInfo(sun.awt.AWTIcon32_security_icon_interim48_png.security_icon_interim48_png);
                icons[3][2] = new IconInfo(sun.awt.AWTIcon32_security_icon_yellow48_png.security_icon_yellow48_png);
            }
        }
        final int sizeIndex = size % icons.length;
        return icons[sizeIndex][num % icons[sizeIndex].length];
    }

    public CWarningWindow(final Window _ownerWindow, final LWWindowPeer _ownerPeer) {
        super();

        this.ownerPeer = new WeakReference<LWWindowPeer>(_ownerPeer);
        this.ownerWindow = _ownerWindow;

        initialize(null, null, _ownerPeer.getPlatformWindow());

        setOpaque(false);

        String warningString = ownerWindow.getWarningString();
        if (warningString != null) {
            contentView.setToolTip(ownerWindow.getWarningString());
        }

        updateIconSize();
    }

    /**
     * @param x,y,w,h coordinates of the untrusted window
     */
    public void reposition(int x, int y, int w, int h) {
        final Point2D point = AWTAccessor.getWindowAccessor().
                calculateSecurityWarningPosition(ownerWindow, x, y, w, h);
        setBounds((int)point.getX(), (int)point.getY(), getWidth(), getHeight());
    }

    public void setVisible(boolean visible, boolean doSchedule) {
        synchronized (scheduler) {
            if (showingTaskHandle != null) {
                showingTaskHandle.cancel(false);
                showingTaskHandle = null;
            }

            if (hidingTaskHandle != null) {
                hidingTaskHandle.cancel(false);
                hidingTaskHandle = null;
            }

            if (visible) {
                if (isVisible()) {
                    currentIcon = 0;
                } else {
                    currentIcon = 2;
                }

                showingTaskHandle = scheduler.schedule(showingTask, 50,
                        TimeUnit.MILLISECONDS);

            } else {
                if (!isVisible()) {
                    return;
                }

                if (doSchedule) {
                    hidingTaskHandle = scheduler.schedule(hidingTask, HIDING_DELAY,
                            TimeUnit.MILLISECONDS);
                } else {
                    hidingTaskHandle = scheduler.schedule(hidingTask, 50,
                            TimeUnit.MILLISECONDS);
                }
            }
        }
    }

    @Override
    public void notifyIconify(boolean iconify) {
    }

    @Override
    public void notifyZoom(boolean isZoomed) {
    }

    @Override
    public void notifyExpose(final Rectangle r) {
        repaint();
    }

    @Override
    public void notifyReshape(int x, int y, int w, int h) {
    }

    @Override
    public void notifyUpdateCursor() {
    }

    @Override
    public void notifyActivation(boolean activation, LWWindowPeer opposite) {
    }

    @Override
    public void notifyNCMouseDown() {
    }

    @Override
    public void notifyMouseEvent(int id, long when, int button, int x, int y,
                                 int screenX, int screenY, int modifiers,
                                 int clickCount, boolean popupTrigger,
                                 byte[] bdata) {
        LWWindowPeer peer = ownerPeer.get();
        if (id == MouseEvent.MOUSE_EXITED) {
            if (peer != null) {
                peer.updateSecurityWarningVisibility();
            }
        } else if(id == MouseEvent.MOUSE_ENTERED) {
            if (peer != null) {
                peer.updateSecurityWarningVisibility();
            }
        }
    }

    public Rectangle getBounds() {
        synchronized (lock) {
            return bounds.getBounds();
        }
    }

    @Override
    public boolean isVisible() {
        synchronized (lock) {
            return visible;
        }
    }

    @Override
    public void setVisible(boolean visible) {
        synchronized (lock) {
            final long nsWindowPtr = getNSWindowPtr();

            // Process parent-child relationship when hiding
            if (!visible) {
                // Unparent myself
                if (owner != null && owner.isVisible()) {
                    CWrapper.NSWindow.removeChildWindow(
                            owner.getNSWindowPtr(), nsWindowPtr);
                }
            }

            // Actually show or hide the window
            if (visible) {
                CWrapper.NSWindow.orderFront(nsWindowPtr);
            } else {
                CWrapper.NSWindow.orderOut(nsWindowPtr);
            }

            this.visible = visible;

            // Manage parent-child relationship when showing
            if (visible) {
                // Add myself as a child
                if (owner != null && owner.isVisible()) {
                    CWrapper.NSWindow.addChildWindow(owner.getNSWindowPtr(),
                            nsWindowPtr, CWrapper.NSWindow.NSWindowAbove);

                    // do not allow security warning to be obscured by other windows
                    if (ownerWindow.isAlwaysOnTop()) {
                        CWrapper.NSWindow.setLevel(nsWindowPtr,
                                CWrapper.NSWindow.NSFloatingWindowLevel);
                    }
                }
            }
        }
    }

    @Override
    public void notifyMouseWheelEvent(long when, int x, int y, int modifiers,
                                      int scrollType, int scrollAmount,
                                      int wheelRotation, double preciseWheelRotation,
                                      byte[] bdata) {
    }

    @Override
    public void notifyKeyEvent(int id, long when, int modifiers, int keyCode,
                               char keyChar, int keyLocation) {
    }

    protected int getInitialStyleBits() {
        int styleBits = 0;
        CPlatformWindow.SET(styleBits, CPlatformWindow.UTILITY, true);
        return styleBits;
    }

    protected void deliverMoveResizeEvent(int x, int y, int width, int height,
                                          boolean byUser) {

        boolean isResize;
        synchronized (lock) {
            isResize = (bounds.width != width || bounds.height != height);
            bounds = new Rectangle(x, y, width, height);
        }

        if (isResize) {
            replaceSurface();
        }

        super.deliverMoveResizeEvent(x, y, width, height, byUser);
    }

    protected CPlatformResponder createPlatformResponder() {
        return new CPlatformResponder(this, false);
    }

    protected CPlatformView createContentView() {
        return new CPlatformView() {
            public GraphicsConfiguration getGraphicsConfiguration() {
                LWWindowPeer peer = ownerPeer.get();
                return peer.getGraphicsConfiguration();
            }

            public Rectangle getBounds() {
                return CWarningWindow.this.getBounds();
            }

            public CGLLayer createCGLayer() {
                return new CGLLayer(null) {
                    public Rectangle getBounds() {
                        return CWarningWindow.this.getBounds();
                    }

                    public GraphicsConfiguration getGraphicsConfiguration() {
                        LWWindowPeer peer = ownerPeer.get();
                        return peer.getGraphicsConfiguration();
                    }

                    public boolean isOpaque() {
                        return false;
                    }
                };
            }
        };
    }

    private void updateIconSize() {
        int newSize = -1;

        if (ownerWindow != null) {
            Insets insets = ownerWindow.getInsets();
            int max = Math.max(insets.top, Math.max(insets.bottom,
                    Math.max(insets.left, insets.right)));
            if (max < 24) {
                newSize = 0;
            } else if (max < 32) {
                newSize = 1;
            } else if (max < 48) {
                newSize = 2;
            } else {
                newSize = 3;
            }
        }
        // Make sure we have a valid size
        if (newSize == -1) {
            newSize = 0;
        }

        synchronized (lock) {
            if (newSize != currentSize) {
                currentSize = newSize;
                IconInfo ico = getSecurityIconInfo(currentSize, 0);
                AWTAccessor.getWindowAccessor().setSecurityWarningSize(
                    ownerWindow, ico.getWidth(), ico.getHeight());
            }
        }
    }

    private final Graphics getGraphics() {
        SurfaceData sd = contentView.getSurfaceData();
        if (ownerWindow == null || sd == null) {
            return null;
        }

        return transformGraphics(new SunGraphics2D(sd, SystemColor.windowText,
                SystemColor.window, ownerWindow.getFont()));
    }


    private void repaint() {
        final Graphics g = getGraphics();
        if (g != null) {
            try {
                ((Graphics2D) g).setComposite(AlphaComposite.Src);
                g.drawImage(getSecurityIconInfo().getImage(), 0, 0, null);
            } finally {
                g.dispose();
            }
        }
    }

    private void replaceSurface() {
        SurfaceData oldData = contentView.getSurfaceData();

        replaceSurfaceData();

        if (oldData != null && oldData != contentView.getSurfaceData()) {
            oldData.flush();
        }
    }

    private int getWidth() {
        return getSecurityIconInfo().getWidth();
    }

    private int getHeight() {
        return getSecurityIconInfo().getHeight();
    }

    private IconInfo getSecurityIconInfo() {
        return getSecurityIconInfo(currentSize, currentIcon);
    }

    private final Runnable hidingTask = new Runnable() {
        public void run() {
            synchronized (lock) {
                setVisible(false);
            }

            synchronized (scheduler) {
                hidingTaskHandle = null;
            }
        }
    };

    private final Runnable showingTask = new Runnable() {
        public void run() {
            synchronized (lock) {
                if (!isVisible()) {
                    setVisible(true);
                }

                repaint();
            }

            synchronized (scheduler) {
                if (currentIcon > 0) {
                    currentIcon--;
                    showingTaskHandle = scheduler.schedule(showingTask, SHOWING_DELAY,
                            TimeUnit.MILLISECONDS);
                } else {
                    showingTaskHandle = null;
                }
            }
        }
    };

    private final ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();

    private ScheduledFuture hidingTaskHandle;
    private ScheduledFuture showingTaskHandle;
}

