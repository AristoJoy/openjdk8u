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
 * (C) Copyright IBM Corp. 1998-2010 - All Rights Reserved
 *
 * Developed at DIT - Government of Bhutan
 *
 * Contact person: Pema Geyleg - <pema_geyleg@druknet.bt>
 *
 * This file is a modification of the ICU file KhmerReordering.cpp
 * by Jens Herden and Javier Sola who have given all their possible rights to IBM and the Governement of Bhutan
 * A first module for Dzongkha was developed by Karunakar under Panlocalisation funding.
 * Assistance for this module has been received from Namgay Thinley, Christopher Fynn and Javier Sola
 *
 */

#ifndef __TIBETANLAYOUTENGINE_H
#define __TIBETANLAYOUTENGINE_H

// #include "LETypes.h"
// #include "LEFontInstance.h"
// #include "LEGlyphFilter.h"
// #include "LayoutEngine.h"
// #include "OpenTypeLayoutEngine.h"

// #include "GlyphSubstitutionTables.h"
// #include "GlyphDefinitionTables.h"
// #include "GlyphPositioningTables.h"

U_NAMESPACE_BEGIN

// class MPreFixups;
// class LEGlyphStorage;

/**
 * This class implements OpenType layout for Dzongkha and Tibetan OpenType fonts
 *
 * @internal
 */
class TibetanOpenTypeLayoutEngine : public OpenTypeLayoutEngine
{
public:
    /**
     * This is the main constructor. It constructs an instance of TibetanOpenTypeLayoutEngine for
     * a particular font, script and language. It takes the GSUB table as a parameter since
     * LayoutEngine::layoutEngineFactory has to read the GSUB table to know that it has an
     * Tibetan OpenType font.
     *
     * @param fontInstance - the font
     * @param scriptCode - the script
     * @param langaugeCode - the language
     * @param gsubTable - the GSUB table
     * @param success - set to an error code if the operation fails
     *
     * @see LayoutEngine::layoutEngineFactory
     * @see OpenTypeLayoutEngine
     * @see ScriptAndLangaugeTags.h for script and language codes
     *
     * @internal
     */
    TibetanOpenTypeLayoutEngine(const LEFontInstance *fontInstance, le_int32 scriptCode, le_int32 languageCode,
                            le_int32 typoFlags, const LEReferenceTo<GlyphSubstitutionTableHeader> &gsubTable, LEErrorCode &success);

    /**
     * This constructor is used when the font requires a "canned" GSUB table which can't be known
     * until after this constructor has been invoked.
     *
     * @param fontInstance - the font
     * @param scriptCode - the script
     * @param langaugeCode - the language
     * @param success - set to an error code if the operation fails
     *
     * @see OpenTypeLayoutEngine
     * @see ScriptAndLangaugeTags.h for script and language codes
     *
     * @internal
     */
    TibetanOpenTypeLayoutEngine(const LEFontInstance *fontInstance, le_int32 scriptCode, le_int32 languageCode,
                                le_int32 typoFlags, LEErrorCode &success);

    /**
     * The destructor, virtual for correct polymorphic invocation.
     *
     * @internal
     */
   virtual ~TibetanOpenTypeLayoutEngine();

    /**
     * ICU "poor man's RTTI", returns a UClassID for the actual class.
     *
     * @internal ICU 3.6
     */
    virtual UClassID getDynamicClassID() const;

    /**
     * ICU "poor man's RTTI", returns a UClassID for this class.
     *
     * @internal ICU 3.6
     */
    static UClassID getStaticClassID();

protected:

    /**
     * This method does Tibetan OpenType character processing. It assigns the OpenType feature
     * tags to the characters, and may generate output characters which have been reordered.
     * It may also split some vowels, resulting in more output characters than input characters.
     *
     * Input parameters:
     * @param chars - the input character context
     * @param offset - the index of the first character to process
     * @param count - the number of characters to process
     * @param max - the number of characters in the input context
     * @param rightToLeft - <code>TRUE</code> if the characters are in a right to left directional run
     * @param glyphStorage - the glyph storage object. The glyph and character index arrays will be set.
     *                       the auxillary data array will be set to the feature tags.
     *
     * Output parameters:
     * @param success - set to an error code if the operation fails
     *
     * @return the output character count
     *
     * @internal
     */
    virtual le_int32 characterProcessing(const LEUnicode chars[], le_int32 offset, le_int32 count, le_int32 max, le_bool rightToLeft,
            LEUnicode *&outChars, LEGlyphStorage &glyphStorage, LEErrorCode &success);

};

U_NAMESPACE_END
#endif

