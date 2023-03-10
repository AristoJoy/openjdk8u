/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

/*
 * COPYRIGHT AND PERMISSION NOTICE
 *
 * Copyright (C) 1991-2012 Unicode, Inc. All rights reserved. Distributed under
 * the Terms of Use in http://www.unicode.org/copyright.html.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of the Unicode data files and any associated documentation (the "Data
 * Files") or Unicode software and any associated documentation (the
 * "Software") to deal in the Data Files or Software without restriction,
 * including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, and/or sell copies of the Data Files or Software, and
 * to permit persons to whom the Data Files or Software are furnished to do so,
 * provided that (a) the above copyright notice(s) and this permission notice
 * appear with all copies of the Data Files or Software, (b) both the above
 * copyright notice(s) and this permission notice appear in associated
 * documentation, and (c) there is clear notice in each modified Data File or
 * in the Software as well as in the documentation associated with the Data
 * File(s) or Software that the data or software has been modified.
 *
 * THE DATA FILES AND SOFTWARE ARE PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT OF
 * THIRD PARTY RIGHTS. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR HOLDERS
 * INCLUDED IN THIS NOTICE BE LIABLE FOR ANY CLAIM, OR ANY SPECIAL INDIRECT OR
 * CONSEQUENTIAL DAMAGES, OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE,
 * DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER
 * TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE
 * OF THE DATA FILES OR SOFTWARE.
 *
 * Except as contained in this notice, the name of a copyright holder shall not
 * be used in advertising or otherwise to promote the sale, use or other
 * dealings in these Data Files or Software without prior written authorization
 * of the copyright holder.
 */

package sun.text.resources.fi;

import sun.util.resources.ParallelListResourceBundle;

public class FormatData_fi extends ParallelListResourceBundle {
    /**
     * Overrides ParallelListResourceBundle
     */
    protected final Object[][] getContents() {
        return new Object[][] {
            { "MonthNames",
                new String[] {
                    "tammikuuta",
                    "helmikuuta",
                    "maaliskuuta",
                    "huhtikuuta",
                    "toukokuuta",
                    "kes\u00e4kuuta",
                    "hein\u00e4kuuta",
                    "elokuuta",
                    "syyskuuta",
                    "lokakuuta",
                    "marraskuuta",
                    "joulukuuta",
                    "",
                }
            },
            { "standalone.MonthNames",
                new String[] {
                    "tammikuu", // january
                    "helmikuu", // february
                    "maaliskuu", // march
                    "huhtikuu", // april
                    "toukokuu", // may
                    "kes\u00e4kuu", // june
                    "hein\u00e4kuu", // july
                    "elokuu", // august
                    "syyskuu", // september
                    "lokakuu", // october
                    "marraskuu", // november
                    "joulukuu", // december
                    "" // month 13 if applicable
                }
            },
            { "MonthAbbreviations",
                new String[] {
                    "tammikuuta",
                    "helmikuuta",
                    "maaliskuuta",
                    "huhtikuuta",
                    "toukokuuta",
                    "kes\u00e4kuuta",
                    "hein\u00e4kuuta",
                    "elokuuta",
                    "syyskuuta",
                    "lokakuuta",
                    "marraskuuta",
                    "joulukuuta",
                    "",
                }
            },
            { "standalone.MonthAbbreviations",
                new String[] {
                    "tammi", // abb january
                    "helmi", // abb february
                    "maalis", // abb march
                    "huhti", // abb april
                    "touko", // abb may
                    "kes\u00e4", // abb june
                    "hein\u00e4", // abb july
                    "elo", // abb august
                    "syys", // abb september
                    "loka", // abb october
                    "marras", // abb november
                    "joulu", // abb december
                    "" // abb month 13 if applicable
                }
            },
            { "MonthNarrows",
                new String[] {
                    "T",
                    "H",
                    "M",
                    "H",
                    "T",
                    "K",
                    "H",
                    "E",
                    "S",
                    "L",
                    "M",
                    "J",
                    "",
                }
            },
            { "standalone.MonthNarrows",
                new String[] {
                    "T",
                    "H",
                    "M",
                    "H",
                    "T",
                    "K",
                    "H",
                    "E",
                    "S",
                    "L",
                    "M",
                    "J",
                    "",
                }
            },
            { "long.Eras",
                new String[] {
                    "ennen Kristuksen syntym\u00e4\u00e4",
                    "j\u00e4lkeen Kristuksen syntym\u00e4n",
                }
            },
            { "Eras",
                new String[] {
                    "eKr.",
                    "jKr.",
                }
            },
            { "narrow.Eras",
                new String[] {
                    "eK",
                    "jK",
                }
            },
            { "DayNames",
                new String[] {
                    "sunnuntai", // Sunday
                    "maanantai", // Monday
                    "tiistai", // Tuesday
                    "keskiviikko", // Wednesday
                    "torstai", // Thursday
                    "perjantai", // Friday
                    "lauantai" // Saturday
                }
            },
            { "standalone.DayNames",
                new String[] {
                    "sunnuntai",
                    "maanantai",
                    "tiistai",
                    "keskiviikko",
                    "torstai",
                    "perjantai",
                    "lauantai",
                }
            },
            { "DayAbbreviations",
                new String[] {
                    "su", // abb Sunday
                    "ma", // abb Monday
                    "ti", // abb Tuesday
                    "ke", // abb Wednesday
                    "to", // abb Thursday
                    "pe", // abb Friday
                    "la" // abb Saturday
                }
            },
            { "standalone.DayAbbreviations",
                new String[] {
                    "su",
                    "ma",
                    "ti",
                    "ke",
                    "to",
                    "pe",
                    "la",
                }
            },
            { "DayNarrows",
                new String[] {
                    "S",
                    "M",
                    "T",
                    "K",
                    "T",
                    "P",
                    "L",
                }
            },
            { "standalone.DayNarrows",
                new String[] {
                    "S",
                    "M",
                    "T",
                    "K",
                    "T",
                    "P",
                    "L",
                }
            },
            { "NumberElements",
                new String[] {
                    ",", // decimal separator
                    "\u00a0", // group (thousands) separator
                    ";", // list separator
                    "%", // percent sign
                    "0", // native 0 digit
                    "#", // pattern digit
                    "-", // minus sign
                    "E", // exponential
                    "\u2030", // per mille
                    "\u221e", // infinity
                    "\ufffd" // NaN
                }
            },
            { "TimePatterns",
                new String[] {
                    "H.mm.ss z", // full time pattern
                    "'klo 'H.mm.ss", // long time pattern
                    "H:mm:ss", // medium time pattern
                    "H:mm", // short time pattern
                }
            },
            { "DatePatterns",
                new String[] {
                    "d. MMMM'ta 'yyyy", // full date pattern
                    "d. MMMM'ta 'yyyy", // long date pattern
                    "d.M.yyyy", // medium date pattern
                    "d.M.yyyy", // short date pattern
                }
            },
            { "DateTimePatterns",
                new String[] {
                    "{1} {0}" // date-time pattern
                }
            },
            { "DateTimePatternChars", "GanjkHmsSEDFwWxhKzZ" },
            { "AmPmMarkers",
                new String[] {
                    "ap.", // am marker
                    "ip."  // pm marker
                }
            },
            { "narrow.AmPmMarkers",
                new String[] {
                    "ap.",
                    "ip.",
                }
            },
        };
    }
}
