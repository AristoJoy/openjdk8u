/*
 * Copyright (c) 2009, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 6867665
 * @bug 6875033
 * @run main/othervm HighestKvno
 * @summary Problem with keytabs with multiple kvno's (key versions)
 */

import sun.security.krb5.internal.ktab.*;
import sun.security.krb5.*;
import java.io.File;
import java.io.FileOutputStream;

public class HighestKvno {

    public static void main(String[] args) throws Exception {
        // kt is a keytab including these entries:
        //
        // me@MAD.LOCAL: Type: 3, KVNO: 4
        // me@MAD.LOCAL: Type: 23, KVNO: 4
        // me@MAD.LOCAL: Type: 16, KVNO: 4
        // me@MAD.LOCAL: Type: 1, KVNO: 5
        // me@MAD.LOCAL: Type: 17, KVNO: 5
        // me@MAD.LOCAL: Type: 18, KVNO: 5
        // me@MAD.LOCAL: Type: 1, KVNO: 3
        // me@MAD.LOCAL: Type: 17, KVNO: 3
        // me@MAD.LOCAL: Type: 18, KVNO: 3
        // he@MAD.LOCAL: Type: 1, KVNO: 1
        // he@MAD.LOCAL: Type: 17, KVNO: 1
        // he@MAD.LOCAL: Type: 18, KVNO: 1
        //
        // This file is created with these steps:
        // 1. Modify JRE's Ktab.java so that adding new entries
        //    does not remove the old one.
        // 2. Run the modified Ktab to create 4 sets of keys
        // 3. Manually hex edit the KVNO as above

        byte[] kt = {
            (byte)0x05, (byte)0x02, (byte)0x00, (byte)0x00,
            (byte)0x00, (byte)0x26, (byte)0x00, (byte)0x01,
            (byte)0x00, (byte)0x09, (byte)0x4D, (byte)0x41,
            (byte)0x44, (byte)0x2E, (byte)0x4C, (byte)0x4F,
            (byte)0x43, (byte)0x41, (byte)0x4C, (byte)0x00,
            (byte)0x02, (byte)0x6D, (byte)0x65, (byte)0x00,
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x4A,
            (byte)0x79, (byte)0x45, (byte)0xCD, (byte)0x04,
            (byte)0x00, (byte)0x03, (byte)0x00, (byte)0x08,
            (byte)0xE6, (byte)0xB0, (byte)0x07, (byte)0xA8,
            (byte)0x5B, (byte)0xF8, (byte)0x73, (byte)0xAD,
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x2E,
            (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x09,
            (byte)0x4D, (byte)0x41, (byte)0x44, (byte)0x2E,
            (byte)0x4C, (byte)0x4F, (byte)0x43, (byte)0x41,
            (byte)0x4C, (byte)0x00, (byte)0x02, (byte)0x6D,
            (byte)0x65, (byte)0x00, (byte)0x00, (byte)0x00,
            (byte)0x00, (byte)0x4A, (byte)0x79, (byte)0x45,
            (byte)0xCD, (byte)0x04, (byte)0x00, (byte)0x17,
            (byte)0x00, (byte)0x10, (byte)0x50, (byte)0x92,
            (byte)0x01, (byte)0x6B, (byte)0xCF, (byte)0x5A,
            (byte)0x2A, (byte)0x7A, (byte)0x4F, (byte)0xE8,
            (byte)0x39, (byte)0xD9, (byte)0x90, (byte)0xB5,
            (byte)0x9C, (byte)0xEB, (byte)0x00, (byte)0x00,
            (byte)0x00, (byte)0x36, (byte)0x00, (byte)0x01,
            (byte)0x00, (byte)0x09, (byte)0x4D, (byte)0x41,
            (byte)0x44, (byte)0x2E, (byte)0x4C, (byte)0x4F,
            (byte)0x43, (byte)0x41, (byte)0x4C, (byte)0x00,
            (byte)0x02, (byte)0x6D, (byte)0x65, (byte)0x00,
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x4A,
            (byte)0x79, (byte)0x45, (byte)0xCD, (byte)0x04,
            (byte)0x00, (byte)0x10, (byte)0x00, (byte)0x18,
            (byte)0xDF, (byte)0xDF, (byte)0x62, (byte)0x86,
            (byte)0x37, (byte)0xCE, (byte)0x29, (byte)0xBA,
            (byte)0xBC, (byte)0x23, (byte)0x15, (byte)0xDC,
            (byte)0x86, (byte)0x7C, (byte)0xB6, (byte)0x89,
            (byte)0x25, (byte)0x25, (byte)0xCD, (byte)0x4A,
            (byte)0x9B, (byte)0xCE, (byte)0xF4, (byte)0xAE,
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x26,
            (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x09,
            (byte)0x4D, (byte)0x41, (byte)0x44, (byte)0x2E,
            (byte)0x4C, (byte)0x4F, (byte)0x43, (byte)0x41,
            (byte)0x4C, (byte)0x00, (byte)0x02, (byte)0x6D,
            (byte)0x65, (byte)0x00, (byte)0x00, (byte)0x00,
            (byte)0x00, (byte)0x4A, (byte)0x79, (byte)0x4B,
            (byte)0x5E, (byte)0x05, (byte)0x00, (byte)0x01,
            (byte)0x00, (byte)0x08, (byte)0xE6, (byte)0xB0,
            (byte)0x07, (byte)0xA8, (byte)0x5B, (byte)0xF8,
            (byte)0x73, (byte)0xAD, (byte)0x00, (byte)0x00,
            (byte)0x00, (byte)0x2E, (byte)0x00, (byte)0x01,
            (byte)0x00, (byte)0x09, (byte)0x4D, (byte)0x41,
            (byte)0x44, (byte)0x2E, (byte)0x4C, (byte)0x4F,
            (byte)0x43, (byte)0x41, (byte)0x4C, (byte)0x00,
            (byte)0x02, (byte)0x6D, (byte)0x65, (byte)0x00,
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x4A,
            (byte)0x79, (byte)0x4B, (byte)0x5E, (byte)0x05,
            (byte)0x00, (byte)0x11, (byte)0x00, (byte)0x10,
            (byte)0xEA, (byte)0xF5, (byte)0xA8, (byte)0x36,
            (byte)0xA5, (byte)0x3E, (byte)0x5F, (byte)0x5C,
            (byte)0x26, (byte)0xE9, (byte)0xDD, (byte)0x8B,
            (byte)0x8C, (byte)0xE8, (byte)0x92, (byte)0x9C,
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x3E,
            (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x09,
            (byte)0x4D, (byte)0x41, (byte)0x44, (byte)0x2E,
            (byte)0x4C, (byte)0x4F, (byte)0x43, (byte)0x41,
            (byte)0x4C, (byte)0x00, (byte)0x02, (byte)0x6D,
            (byte)0x65, (byte)0x00, (byte)0x00, (byte)0x00,
            (byte)0x00, (byte)0x4A, (byte)0x79, (byte)0x4B,
            (byte)0x5E, (byte)0x05, (byte)0x00, (byte)0x12,
            (byte)0x00, (byte)0x20, (byte)0x68, (byte)0xBE,
            (byte)0xD4, (byte)0x17, (byte)0x3A, (byte)0x06,
            (byte)0xE0, (byte)0x0C, (byte)0x62, (byte)0x11,
            (byte)0xB7, (byte)0x53, (byte)0x1B, (byte)0x3E,
            (byte)0xB2, (byte)0x6B, (byte)0x0D, (byte)0x48,
            (byte)0xD8, (byte)0x52, (byte)0x5A, (byte)0x4C,
            (byte)0xBE, (byte)0x24, (byte)0xBB, (byte)0x3D,
            (byte)0xC1, (byte)0x74, (byte)0x69, (byte)0xDA,
            (byte)0x34, (byte)0x98, (byte)0x00, (byte)0x00,
            (byte)0x00, (byte)0x26, (byte)0x00, (byte)0x01,
            (byte)0x00, (byte)0x09, (byte)0x4D, (byte)0x41,
            (byte)0x44, (byte)0x2E, (byte)0x4C, (byte)0x4F,
            (byte)0x43, (byte)0x41, (byte)0x4C, (byte)0x00,
            (byte)0x02, (byte)0x6D, (byte)0x65, (byte)0x00,
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x4A,
            (byte)0x79, (byte)0x51, (byte)0x27, (byte)0x03,
            (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x08,
            (byte)0xE6, (byte)0xB0, (byte)0x07, (byte)0xA8,
            (byte)0x5B, (byte)0xF8, (byte)0x73, (byte)0xAD,
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x2E,
            (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x09,
            (byte)0x4D, (byte)0x41, (byte)0x44, (byte)0x2E,
            (byte)0x4C, (byte)0x4F, (byte)0x43, (byte)0x41,
            (byte)0x4C, (byte)0x00, (byte)0x02, (byte)0x6D,
            (byte)0x65, (byte)0x00, (byte)0x00, (byte)0x00,
            (byte)0x00, (byte)0x4A, (byte)0x79, (byte)0x51,
            (byte)0x27, (byte)0x03, (byte)0x00, (byte)0x11,
            (byte)0x00, (byte)0x10, (byte)0xEA, (byte)0xF5,
            (byte)0xA8, (byte)0x36, (byte)0xA5, (byte)0x3E,
            (byte)0x5F, (byte)0x5C, (byte)0x26, (byte)0xE9,
            (byte)0xDD, (byte)0x8B, (byte)0x8C, (byte)0xE8,
            (byte)0x92, (byte)0x9C, (byte)0x00, (byte)0x00,
            (byte)0x00, (byte)0x3E, (byte)0x00, (byte)0x01,
            (byte)0x00, (byte)0x09, (byte)0x4D, (byte)0x41,
            (byte)0x44, (byte)0x2E, (byte)0x4C, (byte)0x4F,
            (byte)0x43, (byte)0x41, (byte)0x4C, (byte)0x00,
            (byte)0x02, (byte)0x6D, (byte)0x65, (byte)0x00,
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x4A,
            (byte)0x79, (byte)0x51, (byte)0x27, (byte)0x03,
            (byte)0x00, (byte)0x12, (byte)0x00, (byte)0x20,
            (byte)0x68, (byte)0xBE, (byte)0xD4, (byte)0x17,
            (byte)0x3A, (byte)0x06, (byte)0xE0, (byte)0x0C,
            (byte)0x62, (byte)0x11, (byte)0xB7, (byte)0x53,
            (byte)0x1B, (byte)0x3E, (byte)0xB2, (byte)0x6B,
            (byte)0x0D, (byte)0x48, (byte)0xD8, (byte)0x52,
            (byte)0x5A, (byte)0x4C, (byte)0xBE, (byte)0x24,
            (byte)0xBB, (byte)0x3D, (byte)0xC1, (byte)0x74,
            (byte)0x69, (byte)0xDA, (byte)0x34, (byte)0x98,
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x26,
            (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x09,
            (byte)0x4D, (byte)0x41, (byte)0x44, (byte)0x2E,
            (byte)0x4C, (byte)0x4F, (byte)0x43, (byte)0x41,
            (byte)0x4C, (byte)0x00, (byte)0x02, (byte)0x68,
            (byte)0x65, (byte)0x00, (byte)0x00, (byte)0x00,
            (byte)0x00, (byte)0x4A, (byte)0x79, (byte)0x54,
            (byte)0xC7, (byte)0x01, (byte)0x00, (byte)0x01,
            (byte)0x00, (byte)0x08, (byte)0x85, (byte)0x5B,
            (byte)0xE3, (byte)0x13, (byte)0x3E, (byte)0xF8,
            (byte)0x76, (byte)0xEC, (byte)0x00, (byte)0x00,
            (byte)0x00, (byte)0x2E, (byte)0x00, (byte)0x01,
            (byte)0x00, (byte)0x09, (byte)0x4D, (byte)0x41,
            (byte)0x44, (byte)0x2E, (byte)0x4C, (byte)0x4F,
            (byte)0x43, (byte)0x41, (byte)0x4C, (byte)0x00,
            (byte)0x02, (byte)0x68, (byte)0x65, (byte)0x00,
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x4A,
            (byte)0x79, (byte)0x54, (byte)0xC7, (byte)0x01,
            (byte)0x00, (byte)0x11, (byte)0x00, (byte)0x10,
            (byte)0xEC, (byte)0xCC, (byte)0x16, (byte)0xCD,
            (byte)0xE8, (byte)0x51, (byte)0x46, (byte)0x4C,
            (byte)0x1B, (byte)0x57, (byte)0xAE, (byte)0x19,
            (byte)0xC3, (byte)0xD2, (byte)0x55, (byte)0x1B,
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x3E,
            (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x09,
            (byte)0x4D, (byte)0x41, (byte)0x44, (byte)0x2E,
            (byte)0x4C, (byte)0x4F, (byte)0x43, (byte)0x41,
            (byte)0x4C, (byte)0x00, (byte)0x02, (byte)0x68,
            (byte)0x65, (byte)0x00, (byte)0x00, (byte)0x00,
            (byte)0x00, (byte)0x4A, (byte)0x79, (byte)0x54,
            (byte)0xC7, (byte)0x01, (byte)0x00, (byte)0x12,
            (byte)0x00, (byte)0x20, (byte)0xAE, (byte)0xBA,
            (byte)0xCB, (byte)0xF5, (byte)0xA8, (byte)0x09,
            (byte)0xC1, (byte)0xB0, (byte)0x2C, (byte)0x2A,
            (byte)0x3D, (byte)0x96, (byte)0x2C, (byte)0x2D,
            (byte)0xF5, (byte)0xFE, (byte)0x65, (byte)0xEC,
            (byte)0x75, (byte)0x72, (byte)0x5B, (byte)0x46,
            (byte)0x84, (byte)0xD7, (byte)0x49, (byte)0x3E,
            (byte)0xF2, (byte)0x27, (byte)0x32, (byte)0x69,
            (byte)0x75, (byte)0x9B,
        };
        System.setProperty("java.security.krb5.conf",
                new File(System.getProperty("test.src"),
                    "../krb5.conf").getAbsolutePath());
        FileOutputStream fout = new FileOutputStream("kt");
        fout.write(kt);
        fout.close();
        KeyTab ktab = KeyTab.getInstance("kt");
        PrincipalName pn = new PrincipalName("me@MAD.LOCAL");
        EncryptionKey[] keys = ktab.readServiceKeys(pn);
        if (keys[0].getKeyVersionNumber() != 5) {
            throw new Exception("Highest not first");
        }
        new File("kt").delete();
    }
}
