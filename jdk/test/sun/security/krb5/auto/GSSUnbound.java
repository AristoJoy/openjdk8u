/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 8001104
 * @summary Unbound SASL service: the GSSAPI/krb5 mech
 * @compile -XDignore.symbol.file GSSUnbound.java
 * @run main/othervm GSSUnbound
 */

import java.security.Security;
import sun.security.jgss.GSSUtil;

// Testing JGSS without JAAS
public class GSSUnbound {

    public static void main(String[] args) throws Exception {

        new OneKDC(null);

        Context c, s;
        c = Context.fromUserPass(OneKDC.USER, OneKDC.PASS, false);
        s = Context.fromThinAir();

        // This is the only setting needed for JGSS without JAAS. The default
        // JAAS config entries are already created by OneKDC.
        System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");

        c.startAsClient(OneKDC.BACKEND, GSSUtil.GSS_KRB5_MECH_OID);
        s.startAsServer(GSSUtil.GSS_KRB5_MECH_OID);

        Context.handshake(c, s);

        Context.transmit("i say high --", c, s);
        Context.transmit("   you say low", s, c);

        s.dispose();
        c.dispose();
    }
}
