/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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
 *
 */

#ifndef SHARE_VM_CLASSFILE_METADATAONSTACKMARK_HPP
#define SHARE_VM_CLASSFILE_METADATAONSTACKMARK_HPP

#include "memory/allocation.hpp"

class Metadata;

// Helper class to mark and unmark metadata used on the stack as either handles
// or executing methods, so that it can't be deleted during class redefinition
// and class unloading.
// This is also used for other things that can be deallocated, like class
// metadata during parsing, relocated methods, and methods in backtraces.
class MetadataOnStackMark : public StackObj {
  NOT_PRODUCT(static bool _is_active;)
 public:
  MetadataOnStackMark();
  ~MetadataOnStackMark();
  static void record(Metadata* m);
};

#endif // SHARE_VM_CLASSFILE_METADATAONSTACKMARK_HPP
