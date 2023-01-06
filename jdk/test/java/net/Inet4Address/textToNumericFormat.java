/*
 * Copyright (c) 2002, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 4749938
 * @summary Bug in the parsing IPv4 literal addresses
*/

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class textToNumericFormat {

    public static void main(String[] args) throws UnknownHostException {
        List goodList = new ArrayList();
        List badList = new ArrayList();
        String goodAddrs[] = {
                           "224.0.1.0",
                           "238.255.255.255",
                           "239.255.255.255" };

        String badAddrs[] = {
                           "238.255.255.2550",
                           "256.255.255.255",
                           "238.255.2550.255",
                           "238.2550.255.255",
                           "2380.255.255.255"};

        for (int i=0; i<goodAddrs.length; i++) {
            try {
                InetAddress ia = InetAddress.getByName(goodAddrs[i]);
            } catch (UnknownHostException e) {
                // shouldn't have come here
                goodList.add(goodAddrs[i]);
            }

        }

        for (int i=0; i<badAddrs.length; i++) {
            try {
                InetAddress ia = InetAddress.getByName(badAddrs[i]);
                // shouldn't have come here
                badList.add(badAddrs[i]);
            } catch (UnknownHostException e) {
                // ignore
            }

        }

        // if either goodList or badList is not empty, throw exception
        if (goodList.size() > 0 || badList.size() > 0) {
            throw new RuntimeException((goodList.size() > 0?
                                        ("Good address not parsed: "+ goodList)
                                        : "") +
                                       (badList.size() > 0 ?
                                        ("Bad Address parsed: "+ badList)
                                        : ""));
        }

    }

}
