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
 * @bug 6639645
 * @summary Modeling type implementing missing interfaces
 * @library /tools/javac/lib
 * @clean MissingGenericInterface2
 * @build JavacTestingAbstractProcessor Generator
 * @compile -XprintRounds -processor Generator TestMissingGenericInterface2.java
 * @run main TestMissingGenericInterface2
 */

import java.util.*;

public class TestMissingGenericInterface2 implements MissingGenericInterface2<Integer,String> {
    public static void main(String... args) {
        new TestMissingGenericInterface2().run();
    }

    @Override
    public void run() {
        Class<?> c = getClass();
        System.out.println("class: " + c);
        System.out.println("superclass: " + c.getSuperclass());
        System.out.println("generic superclass: " +c.getGenericSuperclass());
        System.out.println("interfaces: " + Arrays.asList(c.getInterfaces()));
        System.out.println("generic interfaces: " + Arrays.asList(c.getGenericInterfaces()));
    }

}
