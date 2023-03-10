/*
 * Copyright (c) 2005, 2006, Oracle and/or its affiliates. All rights reserved.
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
 * @bug     6270396 6245699
 * @summary Missing bridge for final method (gives AbstractMethodError at runtime)
 * @compile/fail T6245699b.java
 */

public class T6245699b {
    public static void main(String[] args) {
        IBar b = new Bar();
        String x = b.doIt();
    }

    static class Foo<T> {
        public final T doIt() { return null; }
    }

    static interface IBar {
        String doIt();
    }

    static class Bar extends Foo<String> implements IBar {
        public String doIt() { // assert that a final method can't be overridden
            return null;
        }
    }
}
