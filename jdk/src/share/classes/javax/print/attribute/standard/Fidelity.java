/*
 * Copyright (c) 2000, 2006, Oracle and/or its affiliates. All rights reserved.
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

import javax.print.attribute.Attribute;
import javax.print.attribute.EnumSyntax;
import javax.print.attribute.PrintJobAttribute;
import javax.print.attribute.PrintRequestAttribute;

/**
 * Class Fidelity is a printing attribute class, an enumeration,
 * that indicates whether total fidelity to client supplied print request
 * attributes is required.
 * If FIDELITY_TRUE is specified and a service cannot print the job exactly
 * as specified it must reject the job.
 * If FIDELITY_FALSE is specified a reasonable attempt to print the job is
 * acceptable. If not supplied the default is FIDELITY_FALSE.
 *
 * <P>
 * <B>IPP Compatibility:</B> The IPP boolean value is "true" for FIDELITY_TRUE
 * and "false" for FIDELITY_FALSE. The category name returned by
 * <CODE>getName()</CODE> is the IPP attribute name.  The enumeration's
 * integer value is the IPP enum value.  The <code>toString()</code> method
 * returns the IPP string representation of the attribute value.
 * See <a href="http://www.ietf.org/rfc/rfc2911.txt">RFC 2911</a> Section 15.1 for
 * a fuller description of the IPP fidelity attribute.
 * <P>
 *
 */
public final class Fidelity extends EnumSyntax
        implements PrintJobAttribute, PrintRequestAttribute {

    private static final long serialVersionUID = 6320827847329172308L;

    /**
     * The job must be printed exactly as specified. or else rejected.
     */
    public static final Fidelity
        FIDELITY_TRUE = new Fidelity(0);

    /**
     * The printer should make reasonable attempts to print the job,
     * even if it cannot print it exactly as specified.
     */
    public static final Fidelity
        FIDELITY_FALSE = new Fidelity(1);

    /**
     * Construct a new fidelity enumeration value with the
     * given integer value.
     *
     * @param  value  Integer value.
     */
    protected Fidelity(int value) {
        super (value);
    }

    private static final String[] myStringTable = {
        "true",
        "false"
    };


    private static final Fidelity[] myEnumValueTable = {
        FIDELITY_TRUE,
        FIDELITY_FALSE
    };

    /**
     * Returns the string table for class Fidelity.
     */
    protected String[] getStringTable() {
        return myStringTable;
    }

    /**
     * Returns the enumeration value table for class Fidelity.
     */
    protected EnumSyntax[] getEnumValueTable() {
        return myEnumValueTable;
    }   /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class Fidelity the category is class Fidelity itself.
     *
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return Fidelity.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class Fidelity the category name is
     * <CODE>"ipp-attribute-fidelity"</CODE>.
     *
     * @return  Attribute category name.
     */
    public final String getName() {
        return "ipp-attribute-fidelity";
    }

}
