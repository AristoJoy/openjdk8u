/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 8022162
 * @summary Incorrect signature determination for certain inner class generics
 * @library /tools/javac/lib
 * @build ToolBox
 * @run main IncorrectSignatureDeterminationForInnerClassesTest
 */

import java.nio.file.Files;
import java.nio.file.Paths;

public class IncorrectSignatureDeterminationForInnerClassesTest {

    private static final String DSrc =
        "package p1;\n" +

        "public class D<T> {\n" +
        "}\n" +

        "abstract class Q<T> {\n" +
        "    protected void m(M.E e) {}\n" +

        "    public class M extends D<T> {\n" +
        "        public class E {}\n" +
        "    }\n" +
        "}";

    private static final String HSrc =
        "package p1;\n" +

        "public class H {\n" +
        "    static class EQ extends Q<Object> {\n" +
        "        private void m2(M.E item) {\n" +
        "            m(item);\n" +
        "        }\n" +
        "    }\n" +
        "}";

    public static void main(String args[]) throws Exception {
        new IncorrectSignatureDeterminationForInnerClassesTest().run();
    }

    void run() throws Exception {
        compile();
    }

    void compile() throws Exception {
        Files.createDirectory(Paths.get("classes"));

        ToolBox.JavaToolArgs javacParams =
                new ToolBox.JavaToolArgs()
                .appendArgs("-d", "classes")
                .setSources(DSrc);

        ToolBox.javac(javacParams);

        // compile class H against the class files for classes D and Q
        javacParams =
                new ToolBox.JavaToolArgs()
                .appendArgs("-d", "classes", "-cp", "classes")
                .setSources(HSrc);
        ToolBox.javac(javacParams);
    }

}
