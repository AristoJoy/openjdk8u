/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.apple.laf;

import java.awt.*;
import java.awt.image.*;
import java.util.HashMap;

import com.apple.laf.AquaImageFactory.RecyclableSlicedImageControl;
import com.apple.laf.AquaImageFactory.NineSliceMetrics;
import com.apple.laf.AquaImageFactory.SlicedImageControl;

import sun.awt.image.*;
import sun.java2d.*;
import sun.print.*;
import apple.laf.*;
import apple.laf.JRSUIUtils.NineSliceMetricsProvider;

abstract class AquaPainter <T extends JRSUIState> {
    static <T extends JRSUIState> AquaPainter<T> create(final T state) {
        return new AquaSingleImagePainter<>(state);
    }

    static <T extends JRSUIState> AquaPainter<T> create(final T state, final int minWidth, final int minHeight, final int westCut, final int eastCut, final int northCut, final int southCut) {
        return create(state, minWidth, minHeight, westCut, eastCut, northCut, southCut, true);
    }

    static <T extends JRSUIState> AquaPainter<T> create(final T state, final int minWidth, final int minHeight, final int westCut, final int eastCut, final int northCut, final int southCut, final boolean useMiddle) {
        return create(state, minWidth, minHeight, westCut, eastCut, northCut, southCut, useMiddle, true, true);
    }

    static <T extends JRSUIState> AquaPainter<T> create(final T state, final int minWidth, final int minHeight, final int westCut, final int eastCut, final int northCut, final int southCut, final boolean useMiddle, final boolean stretchHorizontally, final boolean stretchVertically) {
        return create(state, new NineSliceMetricsProvider() {
            @Override
               public NineSliceMetrics getNineSliceMetricsForState(JRSUIState state) {
                return new NineSliceMetrics(minWidth, minHeight, westCut, eastCut, northCut, southCut, useMiddle, stretchHorizontally, stretchVertically);
            }
        });
    }

    static <T extends JRSUIState> AquaPainter<T> create(final T state, final NineSliceMetricsProvider metricsProvider) {
        return new AquaNineSlicingImagePainter<>(state, metricsProvider);
    }

    abstract void paint(Graphics2D g, T stateToPaint);

    final Rectangle boundsRect = new Rectangle();
    final JRSUIControl control;
    T state;
    AquaPainter(final JRSUIControl control, final T state) {
        this.control = control;
        this.state = state;
    }

    final JRSUIControl getControl() {
        control.set(state = state.derive());
        return control;
    }

    final void paint(final Graphics g, final Component c, final int x,
                     final int y, final int w, final int h) {
        boundsRect.setBounds(x, y, w, h);

        final T nextState = state.derive();
        final Graphics2D g2d = getGraphics2D(g);
        if (g2d != null) paint(g2d, nextState);
        state = nextState;
    }

    private static class AquaNineSlicingImagePainter<T extends JRSUIState>
            extends AquaPainter<T> {

        private final HashMap<T, RecyclableJRSUISlicedImageControl> slicedControlImages;
        private final NineSliceMetricsProvider metricsProvider;

        AquaNineSlicingImagePainter(final T state) {
            this(state, null);
        }

        AquaNineSlicingImagePainter(final T state, final NineSliceMetricsProvider metricsProvider) {
            super(new JRSUIControl(false), state);
            this.metricsProvider = metricsProvider;
            slicedControlImages = new HashMap<>();
        }

        @Override
        void paint(final Graphics2D g, final T stateToPaint) {
            if (metricsProvider == null) {
                AquaSingleImagePainter.paintFromSingleCachedImage(g, control, stateToPaint, boundsRect);
                return;
            }

            RecyclableJRSUISlicedImageControl slicesRef = slicedControlImages.get(stateToPaint);
            if (slicesRef == null) {
                final NineSliceMetrics metrics = metricsProvider.getNineSliceMetricsForState(stateToPaint);
                if (metrics == null) {
                    AquaSingleImagePainter.paintFromSingleCachedImage(g, control, stateToPaint, boundsRect);
                    return;
                }
                slicesRef = new RecyclableJRSUISlicedImageControl(control, stateToPaint, metrics);
                slicedControlImages.put(stateToPaint, slicesRef);
            }
            final SlicedImageControl slices = slicesRef.get();
            slices.paint(g, boundsRect.x, boundsRect.y, boundsRect.width, boundsRect.height);
        }
    }

    private static final class AquaSingleImagePainter<T extends JRSUIState>
            extends AquaPainter<T> {

        AquaSingleImagePainter(final T state) {
            super(new JRSUIControl(false), state);
        }

        @Override
        void paint(final Graphics2D g, final T stateToPaint) {
            paintFromSingleCachedImage(g, control, stateToPaint, boundsRect);
        }

        static void paintFromSingleCachedImage(final Graphics2D g,
                final JRSUIControl control, final JRSUIState controlState,
                final Rectangle bounds) {
            if (bounds.width <= 0 || bounds.height <= 0) {
                return;
            }

            int scale = 1;
            if (g instanceof SunGraphics2D) {
                scale = ((SunGraphics2D) g).surfaceData.getDefaultScale();
            }
            final GraphicsConfiguration config = g.getDeviceConfiguration();
            final ImageCache cache = ImageCache.getInstance();
            final int imgW = bounds.width * scale;
            final int imgH = bounds.height * scale;
            BufferedImage img = (BufferedImage) cache.getImage(config, imgW, imgH, scale, controlState);
            if (img == null) {
                img = new BufferedImage(imgW, imgH, BufferedImage.TYPE_INT_ARGB_PRE);
                cache.setImage(img, config, imgW, imgH, scale, controlState);
                final WritableRaster raster = img.getRaster();
                final DataBufferInt buffer = (DataBufferInt) raster.getDataBuffer();

                control.set(controlState);
                control.paint(SunWritableRaster.stealData(buffer, 0),
                        imgW, imgH, 0, 0, bounds.width, bounds.height);
                SunWritableRaster.markDirty(buffer);
            }

            g.drawImage(img, bounds.x, bounds.y, bounds.width, bounds.height, null);
        }
    }

    private static class RecyclableJRSUISlicedImageControl
            extends RecyclableSlicedImageControl {

        private final JRSUIControl control;
        private final JRSUIState state;

        RecyclableJRSUISlicedImageControl(final JRSUIControl control, final JRSUIState state, final NineSliceMetrics metrics) {
            super(metrics);
            this.control = control;
            this.state = state;
        }

        @Override
        protected Image createTemplateImage(int width, int height) {
            BufferedImage image = new BufferedImage(metrics.minW, metrics.minH, BufferedImage.TYPE_INT_ARGB_PRE);

            final WritableRaster raster = image.getRaster();
            final DataBufferInt buffer = (DataBufferInt)raster.getDataBuffer();

            control.set(state);
            control.paint(SunWritableRaster.stealData(buffer, 0), metrics.minW, metrics.minH, 0, 0, metrics.minW, metrics.minH);

            SunWritableRaster.markDirty(buffer);

            return image;
        }
    }

    private Graphics2D getGraphics2D(final Graphics g) {
        try {
            return (SunGraphics2D)g; // doing a blind try is faster than checking instanceof
        } catch (Exception ignored) {
            if (g instanceof PeekGraphics) {
                // if it is a peek just dirty the region
                g.fillRect(boundsRect.x, boundsRect.y, boundsRect.width, boundsRect.height);
            } else if (g instanceof ProxyGraphics2D) {
                final ProxyGraphics2D pg = (ProxyGraphics2D)g;
                final Graphics2D g2d = pg.getDelegate();
                if (g2d instanceof SunGraphics2D) {
                    return g2d;
                }
            } else if (g instanceof Graphics2D) {
                return (Graphics2D) g;
            }
        }

        return null;
    }
}
