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

/*
 * @bug 4325590
 * @summary Verify that superclass data is not lost when incoming superclass
 *          descriptor is matched with local class that is not a superclass of
 *          the deserialized instance's class.
 */

import java.io.*;
import java.net.*;

class MixedSuperclassStream extends ObjectInputStream {
    MixedSuperclassStream(InputStream in) throws IOException { super(in); }

    protected Class resolveClass(ObjectStreamClass desc)
        throws IOException, ClassNotFoundException
    {
        // resolve A's classdesc to class != B's superclass
        String name = desc.getName();
        if (name.equals("A")) {
            return Class.forName(name, true, Test.ldr1);
        } else if (name.equals("B")) {
            return Class.forName(name, true, Test.ldr2);
        } else {
            return super.resolveClass(desc);
        }
    }
}

public class Test {

    static URLClassLoader ldr1, ldr2;
    static {
        try {
            ldr1 = new URLClassLoader(new URL[] { new URL("file:cb1.jar") });
            ldr2 = new URLClassLoader(new URL[] { new URL("file:cb2.jar") });
        } catch (MalformedURLException ex) {
            throw new Error();
        }
    }

    public static void main(String[] args) throws Exception {
        Runnable a = (Runnable) Class.forName("B", true, ldr1).newInstance();
        a.run();

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(bout);
        oout.writeObject(a);
        oout.close();

        ByteArrayInputStream bin =
            new ByteArrayInputStream(bout.toByteArray());
        ObjectInputStream oin = new MixedSuperclassStream(bin);
        a = (Runnable) oin.readObject();
        a.run();
    }
}
