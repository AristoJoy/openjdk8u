/*
 * Copyright (c) 2008, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
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

/* @test
   @bug 6741426
   @summary Tests reusing Nimbus borders across different components (JComboBox border set on a JTextField)
   @author Peter Zhelezniakov
   @run main Test6741426
*/

import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.*;
import java.awt.image.BufferedImage;


public class Test6741426 implements Runnable {

    static final int WIDTH = 160;
    static final int HEIGHT = 80;

    @Override public void run() {
        JComboBox cb = new JComboBox();
        JTextField tf = new JTextField();
        tf.setBorder(cb.getBorder());
        BufferedImage img =
                new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        tf.setSize(WIDTH, HEIGHT);
        tf.paint(img.getGraphics());
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        SwingUtilities.invokeAndWait(new Test6741426());
    }
}
