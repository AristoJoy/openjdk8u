/*
 * Copyright (c) 2006, 2011, Oracle and/or its affiliates. All rights reserved.
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
 */

import java.net.*;
import java.util.*;
import sun.net.spi.nameservice.*;


public class SimpleNameService implements NameService {
    // host name <-> host addr mapping
    private HashMap<String, String> hosts = new LinkedHashMap<String, String>();

    public void put(String host, String addr) {
        hosts.put(host, addr);
    }

    private static String addrToString(byte addr[]) {
        return Byte.toString(addr[0]) + "." +
               Byte.toString(addr[1]) + "." +
               Byte.toString(addr[2]) + "." +
               Byte.toString(addr[3]);
    }

    public SimpleNameService() {
    }

    public InetAddress[] lookupAllHostAddr(String host) throws UnknownHostException {
        String addr = hosts.get(host);
        if (addr == null) {
            throw new UnknownHostException(host);
        }

        StringTokenizer tokenizer = new StringTokenizer(addr, ".");
        byte addrs[] = new byte[4];
        for (int i = 0; i < 4; i++) {
            addrs[i] = (byte)Integer.parseInt(tokenizer.nextToken());
        }
        InetAddress[] ret = new InetAddress[1];
        ret[0] = InetAddress.getByAddress(host, addrs);
        return ret;
    }

    public String getHostByAddr(byte[] addr) throws UnknownHostException {
        String addrString = addrToString(addr);
        Iterator i = hosts.keySet().iterator();
        while (i.hasNext()) {
            String host = (String)i.next();
            String value = (String)hosts.get(host);
            if (value.equals(addrString)) {
                return host;
            }
        }
        throw new UnknownHostException();
    }
}
