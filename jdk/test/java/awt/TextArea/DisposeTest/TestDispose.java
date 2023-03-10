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
 * Portions Copyright (c) 2012 IBM Corporation
 */

/* @test
 * @bug 7155298
 * @run main/othervm/timeout=60 TestDispose
 * @summary Editable TextArea blocks GUI application from exit.
 * @author Sean Chou
 */

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import sun.awt.SunToolkit;

public class TestDispose {

    public static Frame frame = null;
    public static TextArea textArea = null;
    public static volatile Process worker = null;

    public void testDispose() throws InvocationTargetException,
            InterruptedException {
        SunToolkit toolkit = (SunToolkit) Toolkit.getDefaultToolkit();

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                frame = new JFrame("Test");

                textArea = new TextArea("editable textArea");
                textArea.setEditable(true);
                // textArea.setEditable(false); // this testcase passes if textArea is non-editable

                frame.setLayout(new FlowLayout());
                frame.add(textArea);

                frame.pack();
                frame.setVisible(true);
            }
        });
        toolkit.realSync();

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                frame.dispose();
            }
        });
        toolkit.realSync();
    }

    public static void main(String[] args) throws Exception{
        if(args.length == 0) {
            Runtime.getRuntime().addShutdownHook(new Thread(){
                public void run() {
                    worker.destroy();
                }
            });

            System.out.println(System.getProperty("java.home")+"/bin/java TestDispose workprocess");
            worker = Runtime.getRuntime().exec(System.getProperty("java.home")+"/bin/java TestDispose workprocess");
            worker.waitFor();
            return;
        }

        TestDispose app = new TestDispose();
        app.testDispose();
    }

}
