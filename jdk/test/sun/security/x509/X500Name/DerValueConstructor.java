/*
 * Copyright (c) 1999, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 4228833
 * @summary Make sure constructor that takes DerValue argument works
 */

import sun.security.util.*;
import sun.security.x509.*;

public class DerValueConstructor {

    public static void main(String[] args) throws Exception {
        String name = "CN=anne test";

        DerOutputStream debugDER;
        byte[] ba;

        /*
         * X500Name
         */

        // encode
        X500Name dn = new X500Name(name);
        System.err.println("DEBUG: dn: " + dn.toString());
        debugDER = new DerOutputStream();
        dn.encode(debugDER);
        ba = debugDER.toByteArray();
        System.err.print("DEBUG: encoded X500Name bytes: ");
        System.out.println(toHexString(ba));
        System.err.println();

        // decode
        System.out.println("DEBUG: decoding into X500Name ...");
        X500Name dn1 = new X500Name(new DerValue(ba));
        System.err.println("DEBUG: dn1: " + dn1.toString());
        System.err.println();
        dn1 = new X500Name(ba);
        System.err.println("DEBUG: dn1: " + dn1.toString());
        System.err.println();
        dn1 = new X500Name(new DerInputStream(ba));
        System.err.println("DEBUG: dn1: " + dn1.toString());
        System.err.println();

        /*
         * GeneralName
         */

        // encode
        GeneralName gn = new GeneralName(dn);
        System.err.println("DEBUG: gn: " + gn.toString());
        debugDER = new DerOutputStream();
        gn.encode(debugDER);
        ba = debugDER.toByteArray();
        System.err.print("DEBUG: encoded GeneralName bytes: ");
        System.out.println(toHexString(ba));
        System.err.println();

        // decode
        System.out.println("DEBUG: decoding into GeneralName ...");
        GeneralName gn1 = new GeneralName(new DerValue(ba));
        System.err.println("DEBUG: gn1: " + gn1.toString());
        System.err.println();

        /*
         * GeneralSubtree
         */

        // encode
        GeneralSubtree subTree = new GeneralSubtree(gn, 0, -1);
        System.err.println("DEBUG: subTree: " + subTree.toString());
        debugDER = new DerOutputStream();
        subTree.encode(debugDER);
        ba = debugDER.toByteArray();
        System.err.print("DEBUG: encoded GeneralSubtree bytes: ");
        System.out.println(toHexString(ba));
        System.err.println();

        // decode
        GeneralSubtree debugSubtree = new GeneralSubtree(new DerValue(ba));
    }

    /*
     * Converts a byte to hex digit and writes to the supplied buffer
     */
    private static void byte2hex(byte b, StringBuffer buf) {
        char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                            '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        int high = ((b & 0xf0) >> 4);
        int low = (b & 0x0f);
        buf.append(hexChars[high]);
        buf.append(hexChars[low]);
    }

    /*
     * Converts a byte array to hex string
     */
    private static String toHexString(byte[] block) {
        StringBuffer buf = new StringBuffer();

        int len = block.length;

        for (int i = 0; i < len; i++) {
            byte2hex(block[i], buf);
            if (i < len-1) {
                buf.append(":");
            }
        }
        return buf.toString();
    }
}
