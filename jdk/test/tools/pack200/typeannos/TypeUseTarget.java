/*
 * Copyright (c) 2008, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * @Xtest
 * @bug 6843077 8006775
 * @summary check that type annotations may appear on all type declarations
 * @author Mahmood Ali
 * @compile TypeUseTarget.java
 */

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@B
class TypeUseTarget<K extends @B Object> {
  @B String @B [] field;

  @B String test(@B TypeUseTarget<K> this, @B String param, @B String @B ... vararg) {
    @B Object o = new @B String @B [3];
    TypeUseTarget<@B String> target;
    return (@B String) null;
  }

  <K> @B String genericMethod(K k) { return null; }
  @Decl <K> @B String genericMethod1(K k) { return null; }
  @B @Decl <K> String genericMethod2(K k) { return null; }
  @Decl @B <K> String genericMethod3(K k) { return null; }
  <K> @Decl String genericMethod4(K k) { return null; }
  <K> @B @Decl String genericMethod5(K k) { return null; }
}

@B
interface MyInterface { }

@B
@interface MyAnnotation { }

@Target(ElementType.TYPE_USE)
@interface B { }

@interface Decl { }
