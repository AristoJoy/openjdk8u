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
 * With test.
 *
 * @test
 * @run 
 */

var x = {a: 10, b: 20, c: 30};

var b = "here";

print(b);
print(x.b);

with (x) {
    print(b);
    print(x.b);
    
    b = false;
    
    print(b);
    print(x.b);
    
    y = 200;
}

print(b);
print(x.b);

for (i in x) print(i, x[i]);

var obj = { 
    __noSuchProperty__: function(name) {
        return name.toUpperCase();
    }   
};

with(obj) {
  print(foo);
  print(bar);
}
