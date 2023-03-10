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
 * @summary Test proper functioning of class-defined writeReplace methods.
 */

import java.io.*;

public class WriteReplace {

    static class ReplaceMe implements Serializable {
        private Object obj;
        private boolean writeReplaceCalled = false;

        public ReplaceMe(Object obj) {
            this.obj = obj;
        }

        private Object writeReplace() throws ObjectStreamException {
            if (writeReplaceCalled) {
                throw new Error("multiple calls to writeReplace");
            }
            writeReplaceCalled = true;
            return obj;
        }
    }

    public static void main(String[] args) throws Exception {
        final int nobjs = 10;
        final int nrounds = 10;

        Object common = "foo";
        ReplaceMe[] objs = new ReplaceMe[nobjs];
        for (int i = 0; i < nobjs; i++) {
            objs[i] = new ReplaceMe(common);
        }

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(bout);
        for (int i = 0; i < nrounds; i++) {
            for (int j = 0; j < nobjs; j++) {
                oout.writeObject(objs[j]);
            }
        }

        oout.flush();
        ByteArrayInputStream bin =
            new ByteArrayInputStream(bout.toByteArray());
        ObjectInputStream oin = new ObjectInputStream(bin);
        common = null;

        for (int i = 0; i < nrounds; i++) {
            for (int j = 0; j < nobjs; j++) {
                if (common == null) {
                    common = oin.readObject();
                } else {
                    if (oin.readObject() != common) {
                        throw new Error("incorrect replacement object");
                    }
                }
            }
        }
    }
}
