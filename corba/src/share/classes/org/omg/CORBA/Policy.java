/*
 * Copyright (c) 1998, 2001, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
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
package org.omg.CORBA;


/**
* Interfaces derived from the <tt>Policy</tt> interface allow an
* ORB or CORBA service  access to certain choices that affect
* its operation. This information is accessed in a structured
* manner using interfaces derived from the <tt>Policy</tt>
* interface defined in the CORBA module. A CORBA service does not
* have to use this method of accessing operating options, but
* may choose to do so. The Security Service in particular uses
* this technique for associating Security Policy with objects
* in the system.
* An interface generated by the IDL-to-Java compiler.
* org/omg/CORBA/Policy.java
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from ../../../../../src/share/classes/org/omg/PortableServer/corba.idl
* Saturday, July 17, 1999 12:26:20 AM PDT
*/

public interface Policy extends PolicyOperations, org.omg.CORBA.Object, org.omg.CORBA.portable.IDLEntity
{
} // interface Policy
