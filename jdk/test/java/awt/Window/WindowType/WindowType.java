/*
 * Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved.
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
  @bug 6402325
  @summary Test showing windows of different types
  @author anthony.petrov@sun.com: area=awt.toplevel
  @library ../../regtesthelpers
  @build Util
  @run main WindowType
*/

import java.awt.*;
import test.java.awt.regtesthelpers.Util;

/**
 * WindowType.java
 * Summary: Test showing windows of different types.
 */
public class WindowType {
    private static void test(Window window, Window.Type type) {
        window.setType(type);

        window.setVisible(true);
        Util.waitForIdle(null);
        window.setVisible(false);
    }

    private static void test(Window.Type type) {
        test(new Window((Frame)null), type);
        test(new Frame(), type);
        test(new Dialog((Frame)null), type);
    }

    public static void main(String[] args) {
        test(Window.Type.NORMAL);
        test(Window.Type.UTILITY);
        test(Window.Type.POPUP);
    }
}
