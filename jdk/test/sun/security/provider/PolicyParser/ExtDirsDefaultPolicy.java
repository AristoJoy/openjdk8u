/*
 * Copyright (c) 2004, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 4993819
 * @summary standard extensions path is hard-coded in default
 *      system policy file
 * @ignore run this by hand
 *
 *      java    -Djava.security.manager \
 *              -Djava.ext.dirs=./ExtDirsA:./ExtDirsB \
 *              -Djava.security.debug=parser \
 *              ExtDirsDefaultPolicy
 *
 * To test other varients of the ${{java.ext.dirs}} protocol, remove
 * the grant statement for java.ext.dirs in $JAVA_HOME/lib/security/java.policy
 * and then run against the 3 different policy files.
 *
 *      java    -Djava.security.manager \
 *              -Djava.ext.dirs=./ExtDirsA:./ExtDirsB \
 *              -Djava.security.debug=parser \
 *              -Djava.security.policy=ExtDirs{1,2,3}.policy \
 *              ExtDirsDefaultPolicy
 */

public class ExtDirsDefaultPolicy {
    public static void main(String args[]) throws Exception {
        try {
            ExtDirsA a = new ExtDirsA();
            a.go();
            System.out.println("Test Succeeded");
        } catch (SecurityException se) {
            se.printStackTrace();
            System.out.println("Test Failed");
            throw se;
        }
    }
}
