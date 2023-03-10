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
 * @bug 6263446
 * @summary Tests that double-clicking to edit a cell doesn't select the content.
 * @author Shannon Hickey
 * @run main bug6263446
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import sun.awt.SunToolkit;

public class bug6263446 {

    private static JTable table;
    private static final String FIRST = "AAAAA";
    private static final String SECOND = "BB";
    private static final String ALL = FIRST + " " + SECOND;
    private static Robot robot;

    public static void main(String[] args) throws Exception {
        SunToolkit toolkit = (SunToolkit) Toolkit.getDefaultToolkit();
        robot = new Robot();
        robot.setAutoDelay(50);

        SwingUtilities.invokeAndWait(new Runnable() {

            public void run() {
                createAndShowGUI();
            }
        });


        toolkit.realSync();

        Point point = getClickPoint();
        robot.mouseMove(point.x, point.y);
        toolkit.realSync();

        click(1);
        toolkit.realSync();
        assertEditing(false);

        click(2);
        toolkit.realSync();
        checkSelectedText(null);

        click(3);
        toolkit.realSync();
        checkSelectedText(FIRST);


        click(4);
        toolkit.realSync();
        checkSelectedText(ALL);

        setClickCountToStart(1);

        click(1);
        toolkit.realSync();
        checkSelectedText(null);

        click(2);
        toolkit.realSync();
        checkSelectedText(FIRST);

        click(3);
        toolkit.realSync();
        checkSelectedText(ALL);

        setClickCountToStart(3);

        click(1);
        toolkit.realSync();
        assertEditing(false);

        click(2);
        toolkit.realSync();
        assertEditing(false);

        click(3);
        toolkit.realSync();
        checkSelectedText(null);

        click(4);
        toolkit.realSync();
        checkSelectedText(FIRST);

        click(5);
        toolkit.realSync();
        checkSelectedText(ALL);


        SwingUtilities.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                table.editCellAt(0, 0);
            }
        });

        toolkit.realSync();
        assertEditing(true);

        click(2);
        toolkit.realSync();
        checkSelectedText(FIRST);

    }

    private static void checkSelectedText(String sel) throws Exception {
        assertEditing(true);
        checkSelection(sel);
        cancelCellEditing();
        assertEditing(false);
    }

    private static void setClickCountToStart(final int clicks) throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                DefaultCellEditor editor =
                        (DefaultCellEditor) table.getDefaultEditor(String.class);
                editor.setClickCountToStart(clicks);
            }
        });

    }

    private static void cancelCellEditing() throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                table.getCellEditor().cancelCellEditing();
            }
        });
    }

    private static void checkSelection(final String sel) throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                DefaultCellEditor editor =
                        (DefaultCellEditor) table.getDefaultEditor(String.class);
                JTextField field = (JTextField) editor.getComponent();
                String text = field.getSelectedText();
                if (sel == null) {
                    if (text != null && text.length() != 0) {
                        throw new RuntimeException("Nothing should be selected,"
                                + " but \"" + text + "\" is selected.");
                    }
                } else if (!sel.equals(text)) {
                    throw new RuntimeException("\"" + sel + "\" should be "
                            + "selected, but \"" + text + "\" is selected.");
                }
            }
        });
    }

    private static void assertEditing(final boolean editing) throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                if (editing && !table.isEditing()) {
                    throw new RuntimeException("Table should be editing");
                }
                if (!editing && table.isEditing()) {
                    throw new RuntimeException("Table should not be editing");
                }
            }
        });
    }

    private static Point getClickPoint() throws Exception {
        final Point[] result = new Point[1];
        SwingUtilities.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                Rectangle rect = table.getCellRect(0, 0, false);
                Point point = new Point(rect.x + rect.width / 5,
                        rect.y + rect.height / 2);
                SwingUtilities.convertPointToScreen(point, table);
                result[0] = point;
            }
        });

        return result[0];
    }

    private static void click(int times) {
        robot.delay(500);
        for (int i = 0; i < times; i++) {
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        }
    }

    private static TableModel createTableModel() {
        String[] columnNames = {"Column 0"};
        String[][] data = {{ALL}};

        return new DefaultTableModel(data, columnNames);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("bug6263446");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        table = new JTable(createTableModel());
        frame.add(table);
        frame.pack();
        frame.setVisible(true);
    }
}
