/*
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
 *
 */

/*
 *
 * (C) Copyright IBM Corp.  and others 1998-2013 - All Rights Reserved
 *
 */

#ifndef __LIGATURESUBSTITUTIONPROCESSOR2_H
#define __LIGATURESUBSTITUTIONPROCESSOR2_H

/**
 * \file
 * \internal
 */

#include "LETypes.h"
#include "MorphTables.h"
#include "SubtableProcessor2.h"
#include "StateTableProcessor2.h"
#include "LigatureSubstitution.h"

U_NAMESPACE_BEGIN

class LEGlyphStorage;

#define nComponents 16

class LigatureSubstitutionProcessor2 : public StateTableProcessor2
{
public:
    virtual void beginStateTable();

    virtual le_uint16 processStateEntry(LEGlyphStorage &glyphStorage, le_int32 &currGlyph,
                                        EntryTableIndex2 index, LEErrorCode &success);

    virtual void endStateTable();

    LigatureSubstitutionProcessor2(const LEReferenceTo<MorphSubtableHeader2> &morphSubtableHeader, LEErrorCode &success);
    virtual ~LigatureSubstitutionProcessor2();

    /**
     * ICU "poor man's RTTI", returns a UClassID for the actual class.
     *
     * @stable ICU 2.8
     */
    virtual UClassID getDynamicClassID() const;

    /**
     * ICU "poor man's RTTI", returns a UClassID for this class.
     *
     * @stable ICU 2.8
     */
    static UClassID getStaticClassID();

private:
    LigatureSubstitutionProcessor2();

protected:
    le_uint32 ligActionOffset;
    le_uint32 componentOffset;
    le_uint32 ligatureOffset;

    LEReferenceToArrayOf<LigatureSubstitutionStateEntry2> entryTable;

    le_int32 componentStack[nComponents];
    le_int16 m;

    const LEReferenceTo<LigatureSubstitutionHeader2> ligatureSubstitutionHeader;

};

U_NAMESPACE_END
#endif
