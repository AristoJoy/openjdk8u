/*
 * Copyright (c) 1997, 2003, Oracle and/or its affiliates. All rights reserved.
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
package com.sun.java.swing.plaf.motif;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;


/**
 * Implementation of ScrollBarUI for the Motif Look and Feel
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases.  The current serialization support is appropriate
 * for short term storage or RMI between applications running the same
 * version of Swing.  A future release of Swing will provide support for
 * long term persistence.
 *
 * @author Rich Schiavi
 * @author Hans Muller
 */
public class MotifScrollBarUI extends BasicScrollBarUI
{

    public static ComponentUI createUI(JComponent c) {
        return new MotifScrollBarUI();
    }

    public Dimension getPreferredSize(JComponent c) {
        Insets insets = c.getInsets();
        int dx = insets.left + insets.right;
        int dy = insets.top + insets.bottom;
        return (scrollbar.getOrientation() == JScrollBar.VERTICAL)
            ? new Dimension(dx + 11, dy + 33)
            : new Dimension(dx + 33, dy + 11);
    }

    protected JButton createDecreaseButton(int orientation) {
        return new MotifScrollBarButton(orientation);
    }

    protected JButton createIncreaseButton(int orientation) {
        return new MotifScrollBarButton(orientation);
    }


    public void paintTrack(Graphics g, JComponent c, Rectangle trackBounds)  {
        g.setColor(trackColor);
        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
    }


    public void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds)
    {

        if(thumbBounds.isEmpty() || !scrollbar.isEnabled())     {
            return;
        }

        int w = thumbBounds.width;
        int h = thumbBounds.height;

        g.translate(thumbBounds.x, thumbBounds.y);
        g.setColor(thumbColor);
        g.fillRect(0, 0, w-1, h-1);

        g.setColor(thumbHighlightColor);
        g.drawLine(0, 0, 0, h-1);
        g.drawLine(1, 0, w-1, 0);

        g.setColor(thumbLightShadowColor);
        g.drawLine(1, h-1, w-1, h-1);
        g.drawLine(w-1, 1, w-1, h-2);

        g.translate(-thumbBounds.x, -thumbBounds.y);
    }
}
