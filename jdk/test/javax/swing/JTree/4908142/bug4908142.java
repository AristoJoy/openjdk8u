/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 4908142
 * @summary JList doesn't handle search function appropriately
 * @author Andrey Pikalev
 * @library ../../regtesthelpers
 * @build Util
 * @run main bug4908142
 */
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Callable;
import sun.awt.SunToolkit;

public class bug4908142 {

    private static JTree tree;

    public static void main(String[] args) throws Exception {

        SunToolkit toolkit = (SunToolkit) Toolkit.getDefaultToolkit();
        Robot robot = new Robot();
        robot.setAutoDelay(50);

        SwingUtilities.invokeAndWait(new Runnable() {

            public void run() {
                createAndShowGUI();
            }
        });

        toolkit.realSync();

        SwingUtilities.invokeAndWait(new Runnable() {

            public void run() {
                tree.requestFocus();
                tree.setSelectionRow(0);
            }
        });

        toolkit.realSync();


        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyPress(KeyEvent.VK_D);
        robot.keyRelease(KeyEvent.VK_D);
        toolkit.realSync();


        String sel = Util.invokeOnEDT(new Callable<String>() {

            @Override
            public String call() throws Exception {
                return tree.getLastSelectedPathComponent().toString();
            }
        });

        if (!"aad".equals(sel)) {
            throw new Error("The selected index should be \"aad\", but not " + sel);
        }
    }

    private static void createAndShowGUI() {
        JFrame fr = new JFrame("Test");
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] data = {"aaa", "aab", "aac", "aad", "ade", "bba"};
        final DefaultMutableTreeNode root = new DefaultMutableTreeNode(data[0]);
        for (int i = 1; i < data.length; i++) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(data[i]);
            root.add(node);
        }

        tree = new JTree(root);

        JScrollPane sp = new JScrollPane(tree);
        fr.getContentPane().add(sp);
        fr.setSize(200, 200);
        fr.setVisible(true);
    }
}
