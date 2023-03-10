/*
 * Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 5043626
 * @summary  Tests pressing Home or Ctrl+Home set cursor to invisible element <head>
 * @author Alexander Potochkin
 * @library ../../../../regtesthelpers
 * @build Util
 * @run main bug5043626
 */

import java.awt.Robot;
import java.awt.Toolkit;
import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.BadLocationException;
import java.awt.event.KeyEvent;
import sun.awt.SunToolkit;

public class bug5043626 {

    private static Document doc;
    private static Robot robot;

    public static void main(String[] args) throws Exception {
        SunToolkit toolkit = (SunToolkit) Toolkit.getDefaultToolkit();
        robot = new Robot();

        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

        toolkit.realSync();

        Util.hitKeys(robot, KeyEvent.VK_HOME);
        Util.hitKeys(robot, KeyEvent.VK_1);

        toolkit.realSync();

        String test = getText();

        if (!"1test".equals(test)) {
            throw new RuntimeException("Begin line action set cursor inside <head> tag");
        }

        Util.hitKeys(robot, KeyEvent.VK_HOME);
        Util.hitKeys(robot, KeyEvent.VK_2);

        toolkit.realSync();

        test = getText();

        if (!"21test".equals(test)) {
            throw new RuntimeException("Begin action set cursor inside <head> tag");
        }
    }

    private static String getText() throws Exception {
        final String[] result = new String[1];

        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                try {
                    result[0] = doc.getText(0, doc.getLength()).trim();
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        });

        return result[0];
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setText("test");
        editorPane.setEditable(true);
        frame.add(editorPane);
        frame.pack();
        frame.setVisible(true);
        doc = editorPane.getDocument();
        editorPane.setCaretPosition(doc.getLength());
    }
}
