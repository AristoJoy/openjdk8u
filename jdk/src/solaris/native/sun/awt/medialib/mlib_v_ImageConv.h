/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
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


#ifndef __MLIB_V_IMAGECONV_H
#define __MLIB_V_IMAGECONV_H


#ifdef __cplusplus
extern "C" {
#endif /* __cplusplus */

#if defined ( VIS ) && VIS == 0x200

mlib_status mlib_conv2x2_8nw_f(mlib_image       *dst,
                               const mlib_image *src,
                               const mlib_s32   *kern,
                               mlib_s32         scale,
                               mlib_s32         cmask);

mlib_status mlib_conv3x3_8nw_f(mlib_image       *dst,
                               const mlib_image *src,
                               const mlib_s32   *kern,
                               mlib_s32         scale,
                               mlib_s32         cmask);

mlib_status mlib_convMxN_8nw_f(mlib_image       *dst,
                               const mlib_image *src,
                               mlib_s32         m,
                               mlib_s32         n,
                               mlib_s32         dm,
                               mlib_s32         dn,
                               const mlib_s32   *kern,
                               mlib_s32         scale,
                               mlib_s32         cmask);

#else

mlib_status mlib_conv2x2_8nw_f(mlib_image       *dst,
                               const mlib_image *src,
                               const mlib_s32   *kern,
                               mlib_s32         scale);

mlib_status mlib_conv3x3_8nw_f(mlib_image       *dst,
                               const mlib_image *src,
                               const mlib_s32   *kern,
                               mlib_s32         scale);

mlib_status mlib_convMxN_8nw_f(mlib_image       *dst,
                               const mlib_image *src,
                               mlib_s32         m,
                               mlib_s32         n,
                               mlib_s32         dm,
                               mlib_s32         dn,
                               const mlib_s32   *kern,
                               mlib_s32         scale);

#endif /* defined ( VIS ) && VIS == 0x200 */

#ifdef __cplusplus
}
#endif /* __cplusplus */
#endif /* __MLIB_V_IMAGECONV_H */
