/*
 * Copyright (c) 2001, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 4422122
 * @summary Test that MulticastSocket.getInterface returns the
 *          same InetAddress set by MulticastSocket.setInterface
 */
import java.net.*;
import java.util.Enumeration;
import java.io.IOException;

public class TestInterfaces {

    static final boolean isWindows = System.getProperty("os.name").startsWith("Windows");

    public static void main(String args[]) throws Exception {
        int failures = 0;

        MulticastSocket soc = new MulticastSocket();

        Enumeration nifs = NetworkInterface.getNetworkInterfaces();
        while (nifs.hasMoreElements()) {
            NetworkInterface ni = (NetworkInterface)nifs.nextElement();

            /*
             * Test MulticastSocket.getInterface
             */
            Enumeration addrs = ni.getInetAddresses();
            while (addrs.hasMoreElements()) {
                InetAddress ia = (InetAddress)addrs.nextElement();

                System.out.println("********************************");
                System.out.println("MulticastSocket.setInterface(" + ia + ")");

                try {
                    soc.setInterface(ia);
                } catch (IOException ioe) {
                    System.err.println("Can't set interface to: " + ia
                        + " " + ioe.getMessage());
                    continue;
                }

                InetAddress curr = soc.getInterface();
                if (!curr.equals(ia)) {
                    System.err.println("MulticastSocket.getInterface returned: " + curr);
                    System.err.println("Failed! Expected: " + ia);
                    failures++;
                } else {
                    System.out.println("Passed.");
                }
            }

            /*
             * Test MulticastSocket.getNetworkInterface
             */
            System.out.println("********************************");
            System.out.println("MulticastSocket.setNetworkInterface(" +
                ni.getName() + ")");

            try {
                soc.setNetworkInterface(ni);
            } catch (IOException ioe) {
                System.err.println("Can't set interface to: " + ni.getName()
                        + " " + ioe.getMessage());
                continue;
            }

            // JDK-8022963, Skip (Windows) Teredo Tunneling seudo-Interface
            if (isWindows && ni.getDisplayName().contains("Teredo"))
                continue;

            NetworkInterface curr = soc.getNetworkInterface();
            if (!curr.equals(ni)) {
                System.err.println("MulticastSocket.getNetworkInterface returned: " + curr);
                System.err.println("Failed! Expected: " + ni);
                failures++;
            } else {
                System.out.println("Passed.");
            }

        }

        if (failures > 0) {
            System.out.println("********************************");
            throw new Exception(failures + " test(s) failed!!!");
        }

    }

}
