/*
 * Copyright (c) 2005, 2012, Oracle and/or its affiliates. All rights reserved.
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

/*
 */

/*
 * (C) Copyright Taligent, Inc. 1996, 1997 - All Rights Reserved
 * (C) Copyright IBM Corp. 1996 - 1998 - All Rights Reserved
 *
 * The original version of this source code and documentation
 * is copyrighted and owned by Taligent, Inc., a wholly-owned
 * subsidiary of IBM. These materials are provided under terms
 * of a License Agreement between Taligent and Sun. This technology
 * is protected by multiple US and International patents.
 *
 * This notice and attribution to Taligent may not be removed.
 * Taligent is a registered trademark of Taligent, Inc.
 *
 */

package sun.text.resources.sl;

import java.util.ListResourceBundle;

public class CollationData_sl extends ListResourceBundle {

    protected final Object[][] getContents() {
        return new Object[][] {
            { "Rule",
                /* for sl, default sorting except for the following: */

                /* add d<stroke> between d and e. */
                /* add l<stroke> between l and m. */
                /* add nj "ligature" between n and o. */
                /* add z<abovedot> after z.       */
                "& C < c\u030c , C\u030c "           // C < c-caron
                + "< c\u0301 , C\u0301 "             // c-acute
                + "& D < \u01f3 , \u01f2 , \u01f1 "  // dz
                + "< \u01c6 , \u01c5 , \u01c4 "      // dz-caron
                + "< \u0111 , \u0110 "               // d-stroke
                + "& L < \u0142 , \u0141 "           // l < l-stroke
                + "& N < nj , nJ , Nj , NJ "         // ligature updated
                + "& S < s\u030c , S\u030c "         // s < s-caron
                + "< s\u0301, S\u0301 "              // s-acute
                + "& Z < z\u030c , Z\u030c "         // z < z-caron
                + "< z\u0301 , Z\u0301 "             // z-acute
                + "< z\u0307 , Z\u0307 "             // z-dot-above
            }
        };
    }
}
