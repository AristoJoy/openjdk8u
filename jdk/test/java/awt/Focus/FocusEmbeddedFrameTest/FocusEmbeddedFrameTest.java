/*
 * Copyright (c) 2007, Oracle and/or its affiliates. All rights reserved.
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
  @test
  @bug       6516675
  @summary   Tests that EmbeddedFrame can be focused.
  @author    anton.tarasov: area=awt-focus
  @library   ../../regtesthelpers
  @build     Util
  @run       main FocusEmbeddedFrameTest
*/

import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.lang.reflect.InvocationTargetException;
import test.java.awt.regtesthelpers.Util;

public class FocusEmbeddedFrameTest extends Applet {
    static Frame embedder = new Frame("Embedder");
    static Frame ef = null;
    static volatile boolean passed;

    Robot robot;

    public static void main(String[] args) {
        FocusEmbeddedFrameTest app = new FocusEmbeddedFrameTest();
        app.init();
        app.start();
    }

    public void init() {
        robot = Util.createRobot();
    }

    public void start() {

        if (!"sun.awt.windows.WToolkit".equals(Toolkit.getDefaultToolkit().getClass().getName())) {
            System.out.println("The test is for WToolkit only!");
            return;
        }

        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
                public void eventDispatched(AWTEvent e) {
                   System.err.println("--> " + e);
                }
            }, FocusEvent.FOCUS_EVENT_MASK | WindowEvent.WINDOW_EVENT_MASK);

        embedder.addNotify();

        try {
            ef = Util.createEmbeddedFrame(embedder);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new Error("Test error: couldn't create an EmbeddedFrame!");
        }

        ef.setSize(100, 100);
        ef.setBackground(Color.blue);

        embedder.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    FocusEmbeddedFrameTest.ef.requestFocus();
                }
            });

        ef.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    passed = true;
                }
            });

        embedder.setSize(150, 150);
        embedder.setVisible(true);

        Util.waitForIdle(robot);

        if (!passed) {
            throw new TestFailedException("the EmbeddedFrame didn't get focus on request!");
        }

        System.out.println("Test passed.");
    }
}

class TestFailedException extends RuntimeException {
    TestFailedException(String msg) {
        super("Test failed: " + msg);
    }
}
