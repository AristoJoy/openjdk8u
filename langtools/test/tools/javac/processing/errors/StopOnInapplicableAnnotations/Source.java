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
 */

/*
 * @test
 * @bug 8014016
 * @summary Verify that annotation processors do not get invalid annotations
 * @build Processor
 * @run main Processor Source.java
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@OnMethod
@OnField
class Class<@OnType @OnMethod @OnField T extends @OnType @OnMethod @OnField CharSequence & @OnType @OnMethod @OnField Runnable> extends @OnType @OnMethod @OnField Object {

    @OnType
    @OnTypeUse
    @OnField
    private void testMethod(@OnType @OnField @OnMethod int i) { }

    @OnType
    @OnMethod
    private java.lang.@OnType @OnMethod @OnField String testField;
}

@Target(ElementType.TYPE)
@interface OnType {}

@Target(ElementType.METHOD)
@interface OnMethod {}

@Target(ElementType.TYPE_USE)
@interface OnTypeUse {}

@Target(ElementType.FIELD)
@interface OnField {}
