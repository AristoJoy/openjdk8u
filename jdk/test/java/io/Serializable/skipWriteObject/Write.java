/*
 * Copyright (c) 2000, Oracle and/or its affiliates. All rights reserved.
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

/* @test
 * @bug 4392325
 *
 * @clean Write Read Foo Bar
 * @compile Write.java
 * @run main Write
 * @clean Write Read Foo Bar
 * @compile Read.java
 * @run main Read
 *
 * @summary Ensure that ObjectInputStream can successfully skip over an object
 *          written using a class-defined writeObject method for which the
 *          class is not resolvable.
 */

import java.io.*;

class Foo implements Serializable {
    private static final long serialVersionUID = 0L;
    Bar b = new Bar();
}

class Bar implements Serializable {
    int a, b;
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }
}

public class Write {
    public static void main(String[] args) throws Exception {
        ObjectOutputStream oout =
            new ObjectOutputStream(new FileOutputStream("tmp.ser"));
        oout.writeObject(new Object[] { "before", new Foo(), "after" });
        oout.close();
    }
}
