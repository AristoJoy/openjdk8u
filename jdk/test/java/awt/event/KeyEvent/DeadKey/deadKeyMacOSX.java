/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 7196547
 * @summary Dead Key implementation for KeyEvent on Mac OS X
 * @author alexandr.scherbatiy area=awt.event
 * @run main deadKeyMacOSX
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import sun.awt.OSInfo;
import sun.awt.SunToolkit;

public class deadKeyMacOSX {

    private static SunToolkit toolkit;
    private static volatile int state = 0;

    public static void main(String[] args) throws Exception {

        if (OSInfo.getOSType() != OSInfo.OSType.MACOSX) {
            return;
        }

        toolkit = (SunToolkit) Toolkit.getDefaultToolkit();
        Robot robot = new Robot();
        robot.setAutoDelay(50);

        createAndShowGUI();

        // Pressed keys: Alt + E + A
        // Results:  ALT + VK_DEAD_ACUTE + a with accute accent
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_E);
        robot.keyRelease(KeyEvent.VK_E);
        robot.keyRelease(KeyEvent.VK_ALT);

        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);

        if (state != 3) {
            throw new RuntimeException("Wrong number of key events.");
        }
    }

    static void createAndShowGUI() {
        Frame frame = new Frame();
        frame.setSize(300, 300);
        Panel panel = new Panel();
        panel.addKeyListener(new DeadKeyListener());
        frame.add(panel);
        frame.setVisible(true);
        toolkit.realSync();

        panel.requestFocusInWindow();
        toolkit.realSync();
    }

    static class DeadKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            char keyChar = e.getKeyChar();

            switch (state) {
                case 0:
                    if (keyCode != KeyEvent.VK_ALT) {
                        throw new RuntimeException("Alt is not pressed.");
                    }
                    state++;
                    break;
                case 1:
                    if (keyCode != KeyEvent.VK_DEAD_ACUTE) {
                        throw new RuntimeException("Dead ACUTE is not pressed.");
                    }
                    if (keyChar != 0xB4) {
                        throw new RuntimeException("Pressed char is not dead acute.");
                    }

                    state++;
                    break;
                case 2:
                    if (keyCode != KeyEvent.VK_A) {
                        throw new RuntimeException("A is not pressed.");
                    }
                    if (keyChar != 0xE1) {
                        throw new RuntimeException("A char does not have ACCUTE accent");
                    }
                    state++;
                    break;
                default:
                    throw new RuntimeException("Excessive keyPressed event.");
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            int keyCode = e.getKeyCode();
            char keyChar = e.getKeyChar();

            if (state == 3) {
                if (keyCode != 0) {
                    throw new RuntimeException("Key code should be undefined.");
                }
                if (keyChar != 0xE1) {
                    throw new RuntimeException("A char does not have ACCUTE accent");
                }
            } else {
                throw new RuntimeException("Wron number of keyTyped events.");
            }
        }
    }
}
