/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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

//
// SunJSSE does not support dynamic system properties, no way to re-use
// system properties in samevm/agentvm mode.
//

/*
 * @test
 * @bug 7030966
 * @summary Support AEAD CipherSuites
 * @run main/othervm ShortRSAKeyGCM PKIX TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256
 * @run main/othervm ShortRSAKeyGCM PKIX TLS_RSA_WITH_AES_128_GCM_SHA256
 * @run main/othervm ShortRSAKeyGCM PKIX TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
 * @run main/othervm ShortRSAKeyGCM PKIX TLS_DH_anon_WITH_AES_128_GCM_SHA256
 */

/*
 * Need additional key materials to run the following cases.
 *
 * @run main/othervm ShortRSAKeyGCM PKIX TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256
 * @run main/othervm ShortRSAKeyGCM PKIX TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256
 * @run main/othervm ShortRSAKeyGCM PKIX TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256
 *
 * Need unlimited JCE Unlimited Strength Jurisdiction Policy to run the
 * following cases.
 *
 * @run main/othervm ShortRSAKeyGCM PKIX TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384
 * @run main/othervm ShortRSAKeyGCM PKIX TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384
 * @run main/othervm ShortRSAKeyGCM PKIX TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384
 * @run main/othervm ShortRSAKeyGCM PKIX TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384
 * @run main/othervm ShortRSAKeyGCM PKIX TLS_RSA_WITH_AES_256_GCM_SHA384
 * @run main/othervm ShortRSAKeyGCM PKIX TLS_DHE_RSA_WITH_AES_256_GCM_SHA384
 * @run main/othervm ShortRSAKeyGCM PKIX TLS_DH_anon_WITH_AES_256_GCM_SHA384
 */

import java.net.*;
import java.util.*;
import java.io.*;
import javax.net.ssl.*;
import java.security.Security;
import java.security.KeyStore;
import java.security.KeyFactory;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.*;
import java.security.interfaces.*;
import sun.misc.BASE64Decoder;


public class ShortRSAKeyGCM {

    /*
     * =============================================================
     * Set the various variables needed for the tests, then
     * specify what tests to run on each side.
     */

    /*
     * Should we run the client or server in a separate thread?
     * Both sides can throw exceptions, but do you have a preference
     * as to which side should be the main thread.
     */
    static boolean separateServerThread = true;

    /*
     * Where do we find the keystores?
     */
    // Certificates and key used in the test.
    static String trustedCertStr =
        "-----BEGIN CERTIFICATE-----\n" +
        "MIICkjCCAfugAwIBAgIBADANBgkqhkiG9w0BAQQFADA7MQswCQYDVQQGEwJVUzEN\n" +
        "MAsGA1UEChMESmF2YTEdMBsGA1UECxMUU3VuSlNTRSBUZXN0IFNlcml2Y2UwHhcN\n" +
        "MTEwODE5MDE1MjE5WhcNMzIwNzI5MDE1MjE5WjA7MQswCQYDVQQGEwJVUzENMAsG\n" +
        "A1UEChMESmF2YTEdMBsGA1UECxMUU3VuSlNTRSBUZXN0IFNlcml2Y2UwgZ8wDQYJ\n" +
        "KoZIhvcNAQEBBQADgY0AMIGJAoGBAM8orG08DtF98TMSscjGsidd1ZoN4jiDpi8U\n" +
        "ICz+9dMm1qM1d7O2T+KH3/mxyox7Rc2ZVSCaUD0a3CkhPMnlAx8V4u0H+E9sqso6\n" +
        "iDW3JpOyzMExvZiRgRG/3nvp55RMIUV4vEHOZ1QbhuqG4ebN0Vz2DkRft7+flthf\n" +
        "vDld6f5JAgMBAAGjgaUwgaIwHQYDVR0OBBYEFLl81dnfp0wDrv0OJ1sxlWzH83Xh\n" +
        "MGMGA1UdIwRcMFqAFLl81dnfp0wDrv0OJ1sxlWzH83XhoT+kPTA7MQswCQYDVQQG\n" +
        "EwJVUzENMAsGA1UEChMESmF2YTEdMBsGA1UECxMUU3VuSlNTRSBUZXN0IFNlcml2\n" +
        "Y2WCAQAwDwYDVR0TAQH/BAUwAwEB/zALBgNVHQ8EBAMCAQYwDQYJKoZIhvcNAQEE\n" +
        "BQADgYEALlgaH1gWtoBZ84EW8Hu6YtGLQ/L9zIFmHonUPZwn3Pr//icR9Sqhc3/l\n" +
        "pVTxOINuFHLRz4BBtEylzRIOPzK3tg8XwuLb1zd0db90x3KBCiAL6E6cklGEPwLe\n" +
        "XYMHDn9eDsaq861Tzn6ZwzMgw04zotPMoZN0mVd/3Qca8UJFucE=\n" +
        "-----END CERTIFICATE-----";

    static String targetCertStr =
        "-----BEGIN CERTIFICATE-----\n" +
        "MIICNDCCAZ2gAwIBAgIBDDANBgkqhkiG9w0BAQQFADA7MQswCQYDVQQGEwJVUzEN\n" +
        "MAsGA1UEChMESmF2YTEdMBsGA1UECxMUU3VuSlNTRSBUZXN0IFNlcml2Y2UwHhcN\n" +
        "MTExMTA3MTM1NTUyWhcNMzEwNzI1MTM1NTUyWjBPMQswCQYDVQQGEwJVUzENMAsG\n" +
        "A1UEChMESmF2YTEdMBsGA1UECxMUU3VuSlNTRSBUZXN0IFNlcml2Y2UxEjAQBgNV\n" +
        "BAMTCWxvY2FsaG9zdDBcMA0GCSqGSIb3DQEBAQUAA0sAMEgCQQC3Pb49OSPfOD2G\n" +
        "HSXFCFx1GJEZfqG9ZUf7xuIi/ra5dLjPGAaoY5QF2QOa8VnOriQCXDfyXHxsuRnE\n" +
        "OomxL7EVAgMBAAGjeDB2MAsGA1UdDwQEAwID6DAdBgNVHQ4EFgQUXNCJK3/dtCIc\n" +
        "xb+zlA/JINlvs/MwHwYDVR0jBBgwFoAUuXzV2d+nTAOu/Q4nWzGVbMfzdeEwJwYD\n" +
        "VR0lBCAwHgYIKwYBBQUHAwEGCCsGAQUFBwMCBggrBgEFBQcDAzANBgkqhkiG9w0B\n" +
        "AQQFAAOBgQB2qIDUxA2caMPpGtUACZAPRUtrGssCINIfItETXJZCx/cRuZ5sP4D9\n" +
        "N1acoNDn0hCULe3lhXAeTC9NZ97680yJzregQMV5wATjo1FGsKY30Ma+sc/nfzQW\n" +
        "+h/7RhYtoG0OTsiaDCvyhI6swkNJzSzrAccPY4+ZgU8HiDLzZTmM3Q==\n" +
        "-----END CERTIFICATE-----";

    // Private key in the format of PKCS#8, key size is 512 bits.
    static String targetPrivateKey =
        "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAtz2+PTkj3zg9hh0l\n" +
        "xQhcdRiRGX6hvWVH+8biIv62uXS4zxgGqGOUBdkDmvFZzq4kAlw38lx8bLkZxDqJ\n" +
        "sS+xFQIDAQABAkByx/5Oo2hQ/w2q4L8z+NTRlJ3vdl8iIDtC/4XPnfYfnGptnpG6\n" +
        "ZThQRvbMZiai0xHQPQMszvAHjZVme1eDl3EBAiEA3aKJHynPVCEJhpfCLWuMwX5J\n" +
        "1LntwJO7NTOyU5m8rPECIQDTpzn5X44r2rzWBDna/Sx7HW9IWCxNgUD2Eyi2nA7W\n" +
        "ZQIgJerEorw4aCAuzQPxiGu57PB6GRamAihEAtoRTBQlH0ECIQDN08FgTtnesgCU\n" +
        "DFYLLcw1CiHvc7fZw4neBDHCrC8NtQIgA8TOUkGnpCZlQ0KaI8KfKWI+vxFcgFnH\n" +
        "3fnqsTgaUs4=";

    static char passphrase[] = "passphrase".toCharArray();

    /*
     * Is the server ready to serve?
     */
    volatile static boolean serverReady = false;

    /*
     * Turn on SSL debugging?
     */
    static boolean debug = false;

    /*
     * Define the server side of the test.
     *
     * If the server prematurely exits, serverReady will be set to true
     * to avoid infinite hangs.
     */
    void doServerSide() throws Exception {
        SSLContext context = generateSSLContext(null, targetCertStr,
                                            targetPrivateKey);
        SSLServerSocketFactory sslssf = context.getServerSocketFactory();
        SSLServerSocket sslServerSocket =
            (SSLServerSocket)sslssf.createServerSocket(serverPort);
        serverPort = sslServerSocket.getLocalPort();

        /*
         * Signal Client, we're ready for his connect.
         */
        serverReady = true;

        SSLSocket sslSocket = (SSLSocket)sslServerSocket.accept();
        sslSocket.setEnabledCipherSuites(sslSocket.getSupportedCipherSuites());
        InputStream sslIS = sslSocket.getInputStream();
        OutputStream sslOS = sslSocket.getOutputStream();

        sslIS.read();
        sslOS.write('A');
        sslOS.flush();

        sslSocket.close();
    }

    /*
     * Define the client side of the test.
     *
     * If the server prematurely exits, serverReady will be set to true
     * to avoid infinite hangs.
     */
    void doClientSide() throws Exception {

        /*
         * Wait for server to get started.
         */
        while (!serverReady) {
            Thread.sleep(50);
        }

        SSLContext context = generateSSLContext(trustedCertStr, null, null);
        SSLSocketFactory sslsf = context.getSocketFactory();

        SSLSocket sslSocket =
            (SSLSocket)sslsf.createSocket("localhost", serverPort);

        // enable TLSv1.2 only
        sslSocket.setEnabledProtocols(new String[] {"TLSv1.2"});

        // enable a block cipher
        sslSocket.setEnabledCipherSuites(new String[] {cipherSuite});

        InputStream sslIS = sslSocket.getInputStream();
        OutputStream sslOS = sslSocket.getOutputStream();

        sslOS.write('B');
        sslOS.flush();
        sslIS.read();

        sslSocket.close();
    }

    /*
     * =============================================================
     * The remainder is just support stuff
     */
    private static String tmAlgorithm;        // trust manager
    private static String cipherSuite;        // cipher suite

    private static void parseArguments(String[] args) {
        tmAlgorithm = args[0];
        cipherSuite = args[1];
    }

    private static SSLContext generateSSLContext(String trustedCertStr,
            String keyCertStr, String keySpecStr) throws Exception {

        // generate certificate from cert string
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        // create a key store
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(null, null);

        // import the trused cert
        Certificate trusedCert = null;
        ByteArrayInputStream is = null;
        if (trustedCertStr != null) {
            is = new ByteArrayInputStream(trustedCertStr.getBytes());
            trusedCert = cf.generateCertificate(is);
            is.close();

            ks.setCertificateEntry("RSA Export Signer", trusedCert);
        }

        if (keyCertStr != null) {
            // generate the private key.
            PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(
                                new BASE64Decoder().decodeBuffer(keySpecStr));
            KeyFactory kf = KeyFactory.getInstance("RSA");
            RSAPrivateKey priKey =
                    (RSAPrivateKey)kf.generatePrivate(priKeySpec);

            // generate certificate chain
            is = new ByteArrayInputStream(keyCertStr.getBytes());
            Certificate keyCert = cf.generateCertificate(is);
            is.close();

            Certificate[] chain = null;
            if (trusedCert != null) {
                chain = new Certificate[2];
                chain[0] = keyCert;
                chain[1] = trusedCert;
            } else {
                chain = new Certificate[1];
                chain[0] = keyCert;
            }

            // import the key entry.
            ks.setKeyEntry("Whatever", priKey, passphrase, chain);
        }

        // create SSL context
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmAlgorithm);
        tmf.init(ks);

        SSLContext ctx = SSLContext.getInstance("TLS");
        if (keyCertStr != null && !keyCertStr.isEmpty()) {
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("NewSunX509");
            kmf.init(ks, passphrase);

            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            ks = null;
        } else {
            ctx.init(null, tmf.getTrustManagers(), null);
        }

        return ctx;
    }


    // use any free port by default
    volatile int serverPort = 0;

    volatile Exception serverException = null;
    volatile Exception clientException = null;

    public static void main(String[] args) throws Exception {
        // reset the security property to make sure that the algorithms
        // and keys used in this test are not disabled.
        Security.setProperty("jdk.certpath.disabledAlgorithms", "MD2");

        if (debug) {
            System.setProperty("javax.net.debug", "all");
        }

        /*
         * Get the customized arguments.
         */
        parseArguments(args);

        /*
         * Start the tests.
         */
        new ShortRSAKeyGCM();
    }

    Thread clientThread = null;
    Thread serverThread = null;

    /*
     * Primary constructor, used to drive remainder of the test.
     *
     * Fork off the other side, then do your work.
     */
    ShortRSAKeyGCM() throws Exception {
        try {
            if (separateServerThread) {
                startServer(true);
                startClient(false);
            } else {
                startClient(true);
                startServer(false);
            }
        } catch (Exception e) {
            // swallow for now.  Show later
        }

        /*
         * Wait for other side to close down.
         */
        if (separateServerThread) {
            serverThread.join();
        } else {
            clientThread.join();
        }

        /*
         * When we get here, the test is pretty much over.
         * Which side threw the error?
         */
        Exception local;
        Exception remote;
        String whichRemote;

        if (separateServerThread) {
            remote = serverException;
            local = clientException;
            whichRemote = "server";
        } else {
            remote = clientException;
            local = serverException;
            whichRemote = "client";
        }

        /*
         * If both failed, return the curthread's exception, but also
         * print the remote side Exception
         */
        if ((local != null) && (remote != null)) {
            System.out.println(whichRemote + " also threw:");
            remote.printStackTrace();
            System.out.println();
            throw local;
        }

        if (remote != null) {
            throw remote;
        }

        if (local != null) {
            throw local;
        }
    }

    void startServer(boolean newThread) throws Exception {
        if (newThread) {
            serverThread = new Thread() {
                public void run() {
                    try {
                        doServerSide();
                    } catch (Exception e) {
                        /*
                         * Our server thread just died.
                         *
                         * Release the client, if not active already...
                         */
                        System.err.println("Server died..." + e);
                        serverReady = true;
                        serverException = e;
                    }
                }
            };
            serverThread.start();
        } else {
            try {
                doServerSide();
            } catch (Exception e) {
                serverException = e;
            } finally {
                serverReady = true;
            }
        }
    }

    void startClient(boolean newThread) throws Exception {
        if (newThread) {
            clientThread = new Thread() {
                public void run() {
                    try {
                        doClientSide();
                    } catch (Exception e) {
                        /*
                         * Our client thread just died.
                         */
                        System.err.println("Client died..." + e);
                        clientException = e;
                    }
                }
            };
            clientThread.start();
        } else {
            try {
                doClientSide();
            } catch (Exception e) {
                clientException = e;
            }
        }
    }
}
