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

package p1;

/**
 * Dummy first sentence to suppress breakiterator warning.
 * <p>
 *
 * Case 0 Actual: <A HREF=".">.</A> &nbsp; Current directory
 * <p>
 *
 * Sub-test 1 Actual: <A HREF="{@docroot}">{&#064;docroot}</A> Bare tag - ALL LOWERCASE <br>
 * Sub-test 1 Expect: <A HREF=""></A> <br>
 * (Expect empty string because lowercase docroot is illegal)
 * <p>
 *
 * Sub-test 2 Actual: <A HREF="{@docRoot}">{&#064;docRoot}</A> Bare tag - "R" UPPERCASE <br>
 * Sub-test 2 Expect: <A HREF="..">..</A>
 * <p>
 *
 * Sub-test 3 Actual: <A HREF="{@docRoot}/package-list">{&#064;docRoot}/package-list</A> <br>
 * Sub-test 3 Expect: <A HREF="../package-list">../package-list</A>
 * <p>
 *
 * Sub-test 4 Actual: <A HREF="{@docRoot}/p2/C2.html">{&#064;docRoot}/p2/C2.html</A> <br>
 * Sub-test 4 Expect: <A HREF="../p2/C2.html">../p2/C2.html</A>
 * <p>
 *
 * Sub-test 5 Actual: <A HREF="{@docRoot}/../docs1/p2/C2.html">{&#064;docRoot}/../docs1/p2/C2.html</A> <br>
 * Sub-test 5 Expect: <A HREF="../../docs1/p2/C2.html">../../docs1/p2/C2.html</A>
 * <p>
 *
 * Sub-test 6 Actual: <A HREF="{@docRoot}/p2/package-summary.html#package_description">{&#064;docRoot}/p2/package-summary.html#package_description</A> <br>
 * Sub-test 6 Expect: <A HREF="../p2/package-summary.html#package_description">../p2/package-summary.html#package_description</A>
 * <p>
 *
 * Sub-test 7 Actual: <A HREF="{@docRoot}/../docs1/p2/package-summary.html#package_description">{&#064;docRoot}/../docs1/p2/package-summary.html#package_description</A> <br>
 * Sub-test 7 Expect: <A HREF="../../docs1/p2/package-summary.html#package_description">../../docs1/p2/package-summary.html#package_description</A>
 * <p>
 *
 * <!-- ----------------------------------------------------- -->
 *
 * Allow docRoot to work without a trailing slash for those who had to change their comments
 * to work with the 1.4.0 bug:
 * <p>
 *
 * Sub-test 8 Actual: <A HREF="{@docRoot}p2/C2.html">{&#064;docRoot}p2/C2.html</A> <br>
 * Sub-test 8 Expect: <A HREF="..p2/C2.html">../p2/C2.html</A>
 * <p>
 *
 * Sub-test 9 Actual: <A HREF="{@docRoot}../docs1/p2/C2.html">{&#064;docRoot}../docs1/p2/C2.html</A> <br>
 * Sub-test 9 Expect: <A HREF="..../docs1/p2/C2.html">../../docs1/p2/C2.html</A>
 * <p>
 *
 * Sub-test 10 Actual: <A HREF="{@docRoot}p2/package-summary.html#package_description">{&#064;docRoot}/p2/package-summary.html#package_description</A> <br>
 * Sub-test 10 Expect: <A HREF="..p2/package-summary.html#package_description">../p2/package-summary.html#package_description#package_description</A>
 * <p>
 *
 * Sub-test 11 Actual: <A HREF="{@docRoot}../docs1/p2/package-summary.html#package_description">{&#064;docRoot}/../docs1/p2/package-summary.html#package_description</A> <br>
 * Sub-test 11 Expect: <A HREF="..../docs1/p2/package-summary.html#package_description">../../docs1/p2/package-summary.html#package_description</A>
 *
 */
public class C1 {
}
