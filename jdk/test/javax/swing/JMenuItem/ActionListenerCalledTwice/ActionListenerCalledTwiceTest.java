/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 7160951
 * @summary [macosx] ActionListener called twice for JMenuItem using ScreenMenuBar
 * @author vera.akulova@oracle.com
 * @run main ActionListenerCalledTwiceTest
 */

import sun.awt.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ActionListenerCalledTwiceTest {
    static String menuItems[] = { "Item1", "Item2", "Item3", "Item4", "Item5", "Item6" };
    static KeyStroke keyStrokes[] = {
        KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.META_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0),
        KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.SHIFT_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.META_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, InputEvent.META_MASK)
    };

    static volatile int listenerCallCounter = 0;
    public static void main(String[] args) throws Exception {
        if (sun.awt.OSInfo.getOSType() != sun.awt.OSInfo.OSType.MACOSX) {
            System.out.println("This test is for MacOS only. Automatically passed on other platforms.");
            return;
        }

        System.setProperty("apple.laf.useScreenMenuBar", "true");
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

        SunToolkit toolkit = (SunToolkit) Toolkit.getDefaultToolkit();
        Robot robot = new Robot();
        robot.setAutoDelay(100);

        for (int i = 0; i < menuItems.length; ++i) {
            KeyStroke ks = keyStrokes[i];
            int modKeyCode = getModKeyCode(ks.getModifiers());

            if (modKeyCode != 0) {
                robot.keyPress(modKeyCode);
            }

            robot.keyPress(ks.getKeyCode());
            robot.keyRelease(ks.getKeyCode());

            if (modKeyCode != 0) {
                robot.keyRelease(modKeyCode);
            }

            toolkit.realSync();

            if (listenerCallCounter != 1) {
                throw new Exception("Test failed: ActionListener for " + menuItems[i] +
                    " called " + listenerCallCounter + " times instead of 1!");
            }

            listenerCallCounter = 0;
        }
    }

    private static void createAndShowGUI() {
        JMenu menu = new JMenu("Menu");

        for (int i = 0; i < menuItems.length; ++i) {
            JMenuItem newItem = new JMenuItem(menuItems[i]);
            newItem.setAccelerator(keyStrokes[i]);
            newItem.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        listenerCallCounter++;
                    }
                }
            );
            menu.add(newItem);
        }

        JMenuBar bar = new JMenuBar();
        bar.add(menu);
        JFrame frame = new JFrame("Test");
        frame.setJMenuBar(bar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static int getModKeyCode(int mod) {
        if ((mod & (InputEvent.SHIFT_DOWN_MASK | InputEvent.SHIFT_MASK)) != 0) {
            return KeyEvent.VK_SHIFT;
        }

        if ((mod & (InputEvent.CTRL_DOWN_MASK | InputEvent.CTRL_MASK)) != 0) {
            return KeyEvent.VK_CONTROL;
        }

        if ((mod & (InputEvent.ALT_DOWN_MASK | InputEvent.ALT_MASK)) != 0) {
            return KeyEvent.VK_ALT;
        }

        if ((mod & (InputEvent.META_DOWN_MASK | InputEvent.META_MASK)) != 0) {
            return KeyEvent.VK_META;
        }

        return 0;
    }
}
