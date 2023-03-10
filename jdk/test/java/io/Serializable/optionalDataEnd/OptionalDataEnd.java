/*
 * Copyright (c) 2001, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 4402870
 * @summary Verify that an OptionalDataException with eof == true is thrown
 *          when a call to ObjectInputStream.readObject() attempts to read past
 *          the end of custom data.
 */

import java.io.*;

class Foo implements Serializable {
    int reps;

    Foo(int reps) {
        this.reps = reps;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        for (int i = 0; i < reps; i++) {
            out.writeObject(new Integer(i));
        }
    }

    private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        in.defaultReadObject();
        for (int i = 0; i < reps; i++) {
            in.readObject();
        }
        try {
            in.readObject();
            throw new Error();
        } catch (OptionalDataException ex) {
            if (! (ex.eof && (ex.length == 0))) {
                throw new Error();
            }
        }
    }
}

public class OptionalDataEnd {
    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(bout);
        oout.writeObject(new Foo(5));
        oout.writeObject(new Foo(0));
        oout.close();

        ObjectInputStream oin = new ObjectInputStream(
            new ByteArrayInputStream(bout.toByteArray()));
        oin.readObject();
        oin.readObject();
        oin.close();
    }
}
