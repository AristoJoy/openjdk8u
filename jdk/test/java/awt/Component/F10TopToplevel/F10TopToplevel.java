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
  test
  @bug 6533175
  @summary Block F10 if closest toplevel to keystroke target is not a Frame.
  @author yuri nesterenko : area=awt.toplevel
  @run applet F10TopToplevel.html
*/



/**
 * F10TopToplevel.java
 *
 * summary: tests if F10 has no effect if focused toplevel if not Frame
 */

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;


public class F10TopToplevel extends Applet
{
    //Declare things used in the test, like buttons and labels here
    Frame frame;
    Dialog dialog;
    volatile boolean menuToggled = false;

    public void init()
    {
        setLayout (new BorderLayout ());

    }//End  init()

    public void start ()
    {
        //Get things going.  Request focus, set size, et cetera
        setSize (200,200);
        setVisible(true);
        validate();


        //What would normally go into main() will probably go here.
        //Use System.out.println for diagnostic messages that you want
        //to read after the test is done.
        MenuBar mb;
        Menu menu;
        MenuItem item;
        frame = new Frame("am below");
        frame.setMenuBar( (mb=new MenuBar()) );
        menu = new Menu("nu");
        menu.add((item = new MenuItem("item")));
        item.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent ae ) {
                menuToggled = true;
            }
        });
        mb.add(menu);

        frame.setSize(200,200);
        frame.setLocation( 400,100 );
        frame.setVisible( true );

        dialog = new Dialog(frame);
        dialog.setSize( 100,100 );
        dialog.setVisible(true);

        Robot robot;
        try {
            robot = new Robot();
        } catch(AWTException e){
            throw new RuntimeException("cannot create robot.", e);
        }
        ((sun.awt.SunToolkit)Toolkit.getDefaultToolkit()).realSync();
        robot.mouseMove(dialog.getLocationOnScreen().x + dialog.getWidth()/2,
                        dialog.getLocationOnScreen().y + dialog.getHeight()/2 );
        robot.delay(5);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(5);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(5);
        robot.keyPress(KeyEvent.VK_F10);
        robot.delay(5);
        robot.keyRelease(KeyEvent.VK_F10);
        robot.delay(5);

        robot.delay(10);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(5);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.delay(5);

        ((sun.awt.SunToolkit)Toolkit.getDefaultToolkit()).realSync();

        if(menuToggled) {
            throw new RuntimeException("Oops! Menu should not open.");
        }

    }// start()

}// class F10TopToplevel
