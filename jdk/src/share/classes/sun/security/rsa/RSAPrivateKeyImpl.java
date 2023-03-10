/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
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

package sun.security.rsa;

import java.io.IOException;
import java.math.BigInteger;

import java.security.*;
import java.security.interfaces.*;

import sun.security.util.*;
import sun.security.pkcs.PKCS8Key;

/**
 * Key implementation for RSA private keys, non-CRT form (modulus, private
 * exponent only). For CRT private keys, see RSAPrivateCrtKeyImpl. We need
 * separate classes to ensure correct behavior in instanceof checks, etc.
 *
 * Note: RSA keys must be at least 512 bits long
 *
 * @see RSAPrivateCrtKeyImpl
 * @see RSAKeyFactory
 *
 * @since   1.5
 * @author  Andreas Sterbenz
 */
public final class RSAPrivateKeyImpl extends PKCS8Key implements RSAPrivateKey {

    private static final long serialVersionUID = -33106691987952810L;

    private final BigInteger n;         // modulus
    private final BigInteger d;         // private exponent

    /**
     * Construct a key from its components. Used by the
     * RSAKeyFactory and the RSAKeyPairGenerator.
     */
    RSAPrivateKeyImpl(BigInteger n, BigInteger d) throws InvalidKeyException {
        this.n = n;
        this.d = d;
        RSAKeyFactory.checkRSAProviderKeyLengths(n.bitLength(), null);
        // generate the encoding
        algid = RSAPrivateCrtKeyImpl.rsaId;
        try {
            DerOutputStream out = new DerOutputStream();
            out.putInteger(0); // version must be 0
            out.putInteger(n);
            out.putInteger(0);
            out.putInteger(d);
            out.putInteger(0);
            out.putInteger(0);
            out.putInteger(0);
            out.putInteger(0);
            out.putInteger(0);
            DerValue val =
                new DerValue(DerValue.tag_Sequence, out.toByteArray());
            key = val.toByteArray();
        } catch (IOException exc) {
            // should never occur
            throw new InvalidKeyException(exc);
        }
    }

    // see JCA doc
    public String getAlgorithm() {
        return "RSA";
    }

    // see JCA doc
    public BigInteger getModulus() {
        return n;
    }

    // see JCA doc
    public BigInteger getPrivateExponent() {
        return d;
    }
}
