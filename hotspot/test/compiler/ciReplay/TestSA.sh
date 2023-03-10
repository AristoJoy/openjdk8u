#!/bin/sh
# 
# Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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

##
## @test
## @bug 8011675
## @summary testing of ciReplay with using generated by SA replay.txt 
## @author igor.ignatyev@oracle.com
## @run shell TestSA.sh
##

if [ "${TESTSRC}" = "" ]
then
  TESTSRC=${PWD}
  echo "TESTSRC not set.  Using "${TESTSRC}" as default"
fi
echo "TESTSRC=${TESTSRC}"

## Adding common setup Variables for running shell tests.
. ${TESTSRC}/../../test_env.sh

. ${TESTSRC}/common.sh

generate_replay

${MV} ${replay_data} replay_vm.txt    

if [ -z "${core_file}" -o ! -r "${core_file}" ]
then
    # skip test if MacOS host isn't configured for core dumping
    if [ "$OS" = "Darwin" ]
    then
        if [ ! -d "/cores" ]
        then
            echo TEST SKIPPED: \'/cores\' dir doens\'t exist
            exit 0
        fi
        if [ ! -w "/cores" ]
        then
            echo TEST SKIPPED: \'/cores\' dir exists but is not writable
            exit 0
        fi
    fi
    test_fail 2 "CHECK :: CORE GENERATION" "core wasn't generated on $OS"
fi

echo "dumpreplaydata -a > ${replay_data}" | \
        ${JAVA} ${TESTVMOPTS} \
        -cp ${TESTJAVA}${FS}lib${FS}sa-jdi.jar \
        sun.jvm.hotspot.CLHSDB  ${JAVA} ${core_file}

if [ ! -s ${replay_data} ]
then
    test_fail 1 "CHECK :: REPLAY DATA GENERATION" \
        "replay data wasn't generated by SA"
fi

diff ${replay_data} replay_vm.txt > replay.diff 2>&1
if [ -s replay.diff ]
then
    echo WARNING: replay.txt from SA != replay.txt from VM:
    cat replay.diff
fi

common_tests 10 
${VM_TYPE}_tests 20

cleanup

echo TEST PASSED

