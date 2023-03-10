/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 5074573
 * @summary tests delte-next-word and delete-prev-word actions for all text compnents and all look&feels
 * @author Igor Kushnirskiy
 * @run main bug5074573
 */

import java.util.*;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import sun.awt.SunToolkit;

public class bug5074573 {

    private static JTextComponent textComponent;
    final static String testString = "123 456 789";
    final static String resultString = "456 ";
    final static List<Class<? extends JTextComponent>> textClasses = Arrays.asList(
            JTextArea.class, JEditorPane.class, JTextPane.class,
            JTextField.class, JFormattedTextField.class, JPasswordField.class);

    public static void main(String[] args) throws Exception {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            UIManager.setLookAndFeel(info.getClassName());
            System.out.println(info);
            for (Class<? extends JTextComponent> clazz : textClasses) {
                boolean res = test(clazz);
                if (!res && clazz != JPasswordField.class) {
                    throw new RuntimeException("failed");
                }
            }
        }
    }

    static boolean test(final Class<? extends JTextComponent> textComponentClass) throws Exception {
        SunToolkit toolkit = (SunToolkit) Toolkit.getDefaultToolkit();
        Robot robot = new Robot();
        robot.setAutoWaitForIdle(true);
        robot.setAutoDelay(50);

        SwingUtilities.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                initialize(textComponentClass);
            }
        });

        toolkit.realSync();

        // Remove selection from JTextField components for the Aqua Look & Feel
        if (textComponent instanceof JTextField && "Aqua".equals(UIManager.getLookAndFeel().getID())) {
            SwingUtilities.invokeAndWait(new Runnable() {

                @Override
                public void run() {
                    Caret caret = textComponent.getCaret();
                    int dot = caret.getDot();
                    textComponent.select(dot, dot);
                }
            });

            toolkit.realSync();
        }

        robot.keyPress(getCtrlKey());
        robot.keyPress(KeyEvent.VK_BACK_SPACE);
        robot.keyRelease(KeyEvent.VK_BACK_SPACE);
        robot.keyRelease(getCtrlKey());
        toolkit.realSync();

        SwingUtilities.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                Caret caret = textComponent.getCaret();
                caret.setDot(0);
            }
        });
        toolkit.realSync();

        robot.keyPress(getCtrlKey());
        robot.keyPress(KeyEvent.VK_DELETE);
        robot.keyRelease(KeyEvent.VK_DELETE);
        robot.keyRelease(getCtrlKey());
        toolkit.realSync();

        return resultString.equals(getText());
    }

    private static String getText() throws Exception {
        final String[] result = new String[1];

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                result[0] = textComponent.getText();
            }
        });

        return result[0];
    }

    /**
     * Gets a control key related to the used Look & Feel
     * Returns VK_ALT for Aqua and VK_CONTROL for others
     */
    public static int getCtrlKey() {

        if ("Aqua".equals(UIManager.getLookAndFeel().getID())) {
            return KeyEvent.VK_ALT;
        }

        return KeyEvent.VK_CONTROL;
    }

    private static void initialize(Class<? extends JTextComponent> textComponentClass) {
        try {
            JFrame frame = new JFrame();
            textComponent = textComponentClass.newInstance();
            textComponent.setText(testString);
            frame.add(textComponent);
            frame.pack();
            frame.setVisible(true);
            textComponent.requestFocus();
            Caret caret = textComponent.getCaret();
            caret.setDot(textComponent.getDocument().getLength());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
