#
# Copyright (c) 2004, Oracle and/or its affiliates. All rights reserved.
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

# -*- shell-script -*-
# @summary Check getEnclosingClass and other methods
# @author Peter von der Ahé

rm -rf   pkg1
mkdir    pkg1
mkdir -p pkg1/pkg2

sed '
s/canonical="EnclosingClass/canonical="pkg1.EnclosingClass/g;
s/"class EnclosingClass/"class pkg1.EnclosingClass/g;
s/\/\/package/package pkg1;/g' < ${TESTSRC}/EnclosingClass.java > pkg1/EnclosingClass.java

sed '
s/canonical="EnclosingClass/canonical="pkg1.pkg2.EnclosingClass/g;
s/"class EnclosingClass/"class pkg1.pkg2.EnclosingClass/g;
s/\/\/package/package pkg1.pkg2;/g' < ${TESTSRC}/EnclosingClass.java > pkg1/pkg2/EnclosingClass.java
