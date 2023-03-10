#
# Copyright (c) 2003, 2005, Oracle and/or its affiliates. All rights reserved.
# Copyright 2007, 2008 Red Hat, Inc.
# DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
#
# This code is free software; you can redistribute it and/or modify it
# under the terms of the GNU General Public License version 2 only, as
# published by the Free Software Foundation.
#
# This code is distributed in the hope that it will be useful, but WITHOUT
# ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
# FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
# version 2 for more details (a copy is included in the LICENSE file that
# accompanied this code).
#
# You should have received a copy of the GNU General Public License version
# 2 along with this work; if not, write to the Free Software Foundation,
# Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
#
# Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
# or visit www.oracle.com if you need additional information or have any
# questions.
#
#

# Setup common to Zero (non-Shark) and Shark versions of VM

# The copied fdlibm routines in sharedRuntimeTrig.o must not be optimized
OPT_CFLAGS/sharedRuntimeTrig.o = $(OPT_CFLAGS/NOOPT)
# The copied fdlibm routines in sharedRuntimeTrans.o must not be optimized
OPT_CFLAGS/sharedRuntimeTrans.o = $(OPT_CFLAGS/NOOPT)

# Specify that the CPU is little endian, if necessary
ifeq ($(ZERO_ENDIANNESS), little)
  CFLAGS += -DVM_LITTLE_ENDIAN
endif

# Specify that the CPU is 64 bit, if necessary
ifeq ($(ARCH_DATA_MODEL), 64)
  CFLAGS += -D_LP64=1
endif

# Specify the path to the FFI headers
ifdef ALT_PACKAGE_PATH
  PACKAGE_PATH = $(ALT_PACKAGE_PATH)
else
  ifeq ($(OS_VENDOR),Apple)
    PACKAGE_PATH = /opt/local
  else
    ifeq ($(OS_VENDOR),NetBSD)
      PACKAGE_PATH = /usr/pkg
      LIBS += -Wl,-R${PACKAGE_PATH}/lib
    else
      PACKAGE_PATH = /usr/local
    endif
  endif
endif

CFLAGS += -I$(PACKAGE_PATH)/include
LIBS += -L$(PACKAGE_PATH)/lib -lffi

OPT_CFLAGS/compactingPermGenGen.o = -O1
