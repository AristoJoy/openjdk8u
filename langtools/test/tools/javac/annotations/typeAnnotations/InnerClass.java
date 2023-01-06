import java.lang.annotation.ElementType;

/*
 * Copyright (c) 2009, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 6843077 8006775
 * @summary compiler crashes when visiting inner classes
 * @author Mahmood Ali
 * @compile InnerClass.java
 */

import java.lang.annotation.*;

class InnerClass {

    InnerClass() {}
    InnerClass(Object o) {}

    private void a() {
        new Object() {
            public <R> void method() { }
        };
    }

    Object f1 = new InnerClass() {
            <R> void method() { }
        };

    Object f2 = new InnerClass() {
            <@A R> void method() { }
        };

    Object f3 = new InnerClass(null) {
            <R> void method() { }
        };

    Object f4 = new InnerClass(null) {
            <@A R> void method() { }
        };

    @Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
    @interface A { }
}
