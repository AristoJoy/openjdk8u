/*
 * Copyright (c) 2003, 2010, Oracle and/or its affiliates. All rights reserved.
 * Copyright 2008, 2009 Red Hat, Inc.
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

#ifndef CPU_ZERO_VM_CPPINTERPRETERGENERATOR_ZERO_HPP
#define CPU_ZERO_VM_CPPINTERPRETERGENERATOR_ZERO_HPP

 protected:
  MacroAssembler* assembler() const {
    return _masm;
  }

 public:
  static address generate_entry_impl(MacroAssembler* masm, address entry_point) {
    ZeroEntry *entry = (ZeroEntry *) masm->pc();
    masm->advance(sizeof(ZeroEntry));
    entry->set_entry_point(entry_point);
    return (address) entry;
  }

 protected:
  address generate_entry(address entry_point) {
        return generate_entry_impl(assembler(), entry_point);
  }

#endif // CPU_ZERO_VM_CPPINTERPRETERGENERATOR_ZERO_HPP
