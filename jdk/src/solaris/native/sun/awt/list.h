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
 */
/* $XConsortium: list.h /main/4 1996/10/14 15:04:04 swick $ */
/** ------------------------------------------------------------------------
        This file contains routines for manipulating generic lists.
        Lists are implemented with a "harness".  In other words, each
        node in the list consists of two pointers, one to the data item
        and one to the next node in the list.  The head of the list is
        the same struct as each node, but the "item" ptr is used to point
        to the current member of the list (used by the first_in_list and
        next_in_list functions).

 This file is available under and governed by the GNU General Public
 License version 2 only, as published by the Free Software Foundation.
 However, the following notice accompanied the original version of this
 file:

Copyright (c) 1994 Hewlett-Packard Co.
Copyright (c) 1996  X Consortium

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE X CONSORTIUM BE LIABLE FOR ANY CLAIM, DAMAGES OR
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

Except as contained in this notice, the name of the X Consortium shall
not be used in advertising or otherwise to promote the sale, use or
other dealings in this Software without prior written authorization
from the X Consortium.

    -------------------------------------------------------------------- **/

#include "gdefs.h"

#ifndef LIST_DEF
#define LIST_DEF

#define LESS    -1
#define EQUAL   0
#define GREATER 1
#define DUP_WHOLE_LIST  0
#define START_AT_CURR   1

typedef struct _list_item {
    struct _list_item *next;
    union {
        void *item;              /* in normal list node, pts to data */
        struct _list_item *curr; /* in list head, pts to curr for 1st, next */
    } ptr;
} list, list_item, *list_ptr;

typedef void (*DESTRUCT_FUNC_PTR)(
#if NeedFunctionPrototypes
void *
#endif
);

void zero_list(
#if NeedFunctionPrototypes
          list_ptr
#endif
    );
int32_t add_to_list (
#if NeedFunctionPrototypes
          list_ptr , void *
#endif
    );
list_ptr new_list (
#if NeedFunctionPrototypes
          void
#endif
    );
list_ptr dup_list_head (
#if NeedFunctionPrototypes
          list_ptr , int32_t
#endif
    );
uint32_t list_length(
#if NeedFunctionPrototypes
          list_ptr
#endif
    );
void *delete_from_list (
#if NeedFunctionPrototypes
          list_ptr , void *
#endif
    );
void delete_list(
#if NeedFunctionPrototypes
          list_ptr , int32_t
#endif
    );
void delete_list_destroying (
#if NeedFunctionPrototypes
          list_ptr , DESTRUCT_FUNC_PTR
#endif
    );
void *first_in_list (
#if NeedFunctionPrototypes
          list_ptr
#endif
    );
void *next_in_list (
#if NeedFunctionPrototypes
          list_ptr
#endif
    );
int32_t list_is_empty (
#if NeedFunctionPrototypes
          list_ptr
#endif
    );

#endif
