
/*
 * Copyright (c) 2008, 2012, Oracle and/or its affiliates. All rights reserved.
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

/*
 * @test
 * @bug 4251579
 * @summary  Tests if style sheets are working in JLabel
 * @author Denis Sharypov
 * @run main bug4251579
 */
import java.awt.*;
import javax.swing.*;

import sun.awt.SunToolkit;

public class bug4251579 {

    private static JLabel htmlComponent;

    public static void main(String[] args) throws Exception {
        SunToolkit toolkit = (SunToolkit) Toolkit.getDefaultToolkit();
        final Robot robot = new Robot();
        robot.setAutoDelay(50);

        SwingUtilities.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                createAndShowGUI();
            }
        });

        toolkit.realSync();

        SwingUtilities.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                boolean passed = false;

                Point p = htmlComponent.getLocationOnScreen();
                Dimension d = htmlComponent.getSize();
                int x0 = p.x;
                int y = p.y + d.height / 2;

                for (int x = x0; x < x0 + d.width; x++) {
                    if (robot.getPixelColor(x, y).equals(Color.blue)) {
                        passed = true;
                        break;
                    }
                }

                if (!passed) {
                    throw new RuntimeException("Test failed.");
                }

            }
        });
    }

    private static void createAndShowGUI() {

        String htmlText =
                "<html>"
                + "<head><style> .blue{ color:blue; } </style></head>"
                + "<body"
                + "<P class=\"blue\"> should be rendered with BLUE class definition</P>"
                + "</body>";

        JFrame mainFrame = new JFrame("bug4251579");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        htmlComponent = new JLabel(htmlText);
        mainFrame.getContentPane().add(htmlComponent);

        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
