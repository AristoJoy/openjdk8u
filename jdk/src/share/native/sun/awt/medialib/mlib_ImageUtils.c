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


#include "mlib_image.h"

/***************************************************************/
typedef union {
  mlib_d64 db;
  struct {
#ifdef _LITTLE_ENDIAN
    mlib_s32 int1, int0;
#else
    mlib_s32 int0, int1;
#endif
  } two_int;
} type_union_mlib_d64;

#define DVAIN52 4.503599627370496e15

/***************************************************************/
mlib_s32 mlib_ilogb(mlib_d64 X)
{
  type_union_mlib_d64 arg;
  mlib_s32 n;

  if (X == 0.0)
    return -MLIB_S32_MAX;
  arg.db = X;
  n = arg.two_int.int0 & 0x7ff00000;
  if (n)
    n = (n < 0x7ff00000) ? (n >> 20) - 1023 : MLIB_S32_MAX;
  else {
    arg.db = X * DVAIN52;
    n = ((arg.two_int.int0 & 0x7ff00000) >> 20) - 1075;
  }
  return n;
}

/***************************************************************/
