/*
 * Copyright (c) 2007, Oracle and/or its affiliates. All rights reserved.
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

#ifndef OGLBufImgOps_h_Included
#define OGLBufImgOps_h_Included

#include "OGLContext.h"

void OGLBufImgOps_EnableConvolveOp(OGLContext *oglc, jlong pSrcOps,
                                   jboolean edgeZeroFill,
                                   jint kernelWidth, jint KernelHeight,
                                   unsigned char *kernelVals);
void OGLBufImgOps_DisableConvolveOp(OGLContext *oglc);
void OGLBufImgOps_EnableRescaleOp(OGLContext *oglc, jlong pSrcOps,
                                  jboolean nonPremult,
                                  unsigned char *scaleFactors,
                                  unsigned char *offsets);
void OGLBufImgOps_DisableRescaleOp(OGLContext *oglc);
void OGLBufImgOps_EnableLookupOp(OGLContext *oglc, jlong pSrcOps,
                                 jboolean nonPremult, jboolean shortData,
                                 jint numBands, jint bandLength, jint offset,
                                 void *tableValues);
void OGLBufImgOps_DisableLookupOp(OGLContext *oglc);

#endif /* OGLBufImgOps_h_Included */
