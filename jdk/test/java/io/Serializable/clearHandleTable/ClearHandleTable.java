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
 * @bug 4332184
 * @summary Ensure that ObjectOutputStream properly releases strong references
 *          to written objects when reset() is called.
 */

import java.io.*;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ClearHandleTable {
    public static void main(String[] args) throws Exception {
        final int nreps = 100;
        ObjectOutputStream oout =
            new ObjectOutputStream(new ByteArrayOutputStream());
        WeakReference[] refs = new WeakReference[nreps];

        for (int i = 0; i < nreps; i++) {
            String str = new String("blargh");
            oout.writeObject(str);
            refs[i] = new WeakReference(str);
        }

        oout.reset();
        exhaustMemory();

        for (int i = 0; i < nreps; i++) {
            if (refs[i].get() != null) {
                throw new Error("failed to garbage collect object " + i);
            }
        }
    }

    static void exhaustMemory() {
        ArrayList blob = new ArrayList();
        try {
            for (;;) {
                blob.add(new int[0xFFFF]);
            }
        } catch (OutOfMemoryError e) {
        }
    }
}
