/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
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

/**
 * JDK-8024174: Setting __proto__ property in Object literal should be supported *
 * @test
 * @run
 */

var p = { foo: function() { print("p.foo"); } };

var obj = {
    __proto__ : p,
    bar: 44
};

if (obj.__proto__ !== p || Object.getPrototypeOf(obj) !== p) {
    fail("obj.__proto__ was not set inside literal");
}

if (typeof(obj.foo) !== 'function' || obj.foo !== p.foo) {
    fail("'obj' failed to inherit 'foo' from 'p'");
}

var obj2 = {
    __proto__: null
};

if (obj2.__proto__ !== null || Object.getPrototypeOf(obj2) !== null) {
    fail("obj2.__proto__ was not set to null inside literal");
}
