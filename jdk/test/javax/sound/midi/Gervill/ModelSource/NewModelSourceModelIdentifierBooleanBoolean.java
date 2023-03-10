/*
 * Copyright (c) 2007, 2013, Oracle and/or its affiliates. All rights reserved.
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

/* @test
   @summary Test ModelSource(ModelIdentifier,boolean,boolean) constructor */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.sound.sampled.*;

import com.sun.media.sound.*;

public class NewModelSourceModelIdentifierBooleanBoolean {

    public static void main(String[] args) throws Exception {
        ModelSource src = new ModelSource(ModelSource.SOURCE_NOTEON_KEYNUMBER,ModelStandardTransform.DIRECTION_MAX2MIN,ModelStandardTransform.POLARITY_BIPOLAR);
        if(src.getIdentifier() != ModelSource.SOURCE_NOTEON_KEYNUMBER)
            throw new RuntimeException("src.getIdentifier() doesn't return ModelSource.SOURCE_NOTEON_KEYNUMBER!");
        if(!(src.getTransform() instanceof ModelStandardTransform))
            throw new RuntimeException("src.getTransform() doesn't return object which is instance of ModelStandardTransform!");
        ModelStandardTransform trans = (ModelStandardTransform)src.getTransform();
        if(trans.getDirection() != ModelStandardTransform.DIRECTION_MAX2MIN)
            throw new RuntimeException("trans.getDirection() doesn't return ModelStandardTransform.DIRECTION_MAX2MIN!");
        if(trans.getPolarity() != ModelStandardTransform.POLARITY_BIPOLAR)
            throw new RuntimeException("trans.getPolarity() doesn't return ModelStandardTransform.POLARITY_BIPOLAR!");
    }
}
