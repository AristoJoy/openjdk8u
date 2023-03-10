#!/bin/sh

#
# Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
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


# @test
# @bug 7104647
# @summary Unit test for jcmd utility
#
# @library ../common
# @build SimpleApplication ShutdownSimpleApplication
# @run shell jcmd-help-help.sh

. ${TESTSRC}/../common/CommonSetup.sh
. ${TESTSRC}/../common/ApplicationSetup.sh

# Start application and use PORTFILE for coordination
PORTFILE="${TESTCLASSES}"/shutdown.port
startApplication SimpleApplication "${PORTFILE}"

# all return statuses are checked in this test
set +e

failed=0

# help help
rm -f jcmd.out 2>/dev/null
${JCMD} -J-XX:+UsePerfData $appJavaPid help help | awk '{ if (NR>1) print $0;}' > jcmd.out 2>&1
echo jcmd.out
diff -w jcmd.out ${TESTSRC}/help_help.out
if [ $? != 0 ]; then
  echo "Output of jcmd [pid] help help differ from expected output. Failed."
  rm -f jcmd.out 2>/dev/null
  failed=1;
fi

set -e

stopApplication "${PORTFILE}"
waitForApplication

exit $failed
