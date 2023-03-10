/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
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
package javax.print.attribute.standard;

import java.util.Locale;
import javax.print.attribute.Attribute;
import javax.print.attribute.EnumSyntax;

/**
 * Class MediaName is a subclass of Media, a printing attribute class (an
 * enumeration) that specifies the media for a print job as a name.
 * <P>
 * This attribute can be used instead of specifying MediaSize or MediaTray.
 * <p>
 * Class MediaName currently declares a few standard media names.
 * Implementation- or site-defined names for a media name attribute may also
 * be created by defining a subclass of class MediaName.
 * <P>
 * <B>IPP Compatibility:</B> MediaName is a representation class for
 * values of the IPP "media" attribute which names media.
 * <P>
 *
 */
public class MediaName extends Media implements Attribute {

    private static final long serialVersionUID = 4653117714524155448L;

    /**
     *  white letter paper.
     */
    public static final MediaName NA_LETTER_WHITE = new MediaName(0);

    /**
     *  letter transparency.
     */
    public static final MediaName NA_LETTER_TRANSPARENT = new MediaName(1);

    /**
     * white A4 paper.
     */
    public static final MediaName ISO_A4_WHITE = new MediaName(2);


    /**
     *  A4 transparency.
     */
    public static final MediaName ISO_A4_TRANSPARENT= new MediaName(3);


    /**
     * Constructs a new media name enumeration value with the given integer
     * value.
     *
     * @param  value  Integer value.
     */
    protected MediaName(int value) {
        super (value);
    }

    private static final String[] myStringTable = {
        "na-letter-white",
        "na-letter-transparent",
        "iso-a4-white",
        "iso-a4-transparent"
    };

    private static final MediaName[] myEnumValueTable = {
        NA_LETTER_WHITE,
        NA_LETTER_TRANSPARENT,
        ISO_A4_WHITE,
        ISO_A4_TRANSPARENT
    };

    /**
     * Returns the string table for class MediaTray.
     * @return the String table.
     */
    protected String[] getStringTable()
    {
        return (String[])myStringTable.clone();
    }

    /**
     * Returns the enumeration value table for class MediaTray.
     * @return the enumeration value table.
     */
    protected EnumSyntax[] getEnumValueTable() {
        return (EnumSyntax[])myEnumValueTable.clone();
    }

}
