/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.xml.internal.xsom.parser;

/**
 * Enumeration used to represent the type of the schema component
 * that is being parsed when the AnnotationParser is called.
 *
 * @author
 *     Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
final public class AnnotationContext {

    /** Display name of the context. */
    private final String name;

    private AnnotationContext( String _name ) {
        this.name = _name;
    }

    public String toString() { return name; }



    public static final AnnotationContext SCHEMA
        = new AnnotationContext("schema");
    public static final AnnotationContext NOTATION
        = new AnnotationContext("notation");
    public static final AnnotationContext ELEMENT_DECL
        = new AnnotationContext("element");
    public static final AnnotationContext IDENTITY_CONSTRAINT
        = new AnnotationContext("identityConstraint");
    public static final AnnotationContext XPATH
        = new AnnotationContext("xpath");
    public static final AnnotationContext MODELGROUP_DECL
        = new AnnotationContext("modelGroupDecl");
    public static final AnnotationContext SIMPLETYPE_DECL
        = new AnnotationContext("simpleTypeDecl");
    public static final AnnotationContext COMPLEXTYPE_DECL
        = new AnnotationContext("complexTypeDecl");
    public static final AnnotationContext PARTICLE
        = new AnnotationContext("particle");
    public static final AnnotationContext MODELGROUP
        = new AnnotationContext("modelGroup");
    public static final AnnotationContext ATTRIBUTE_USE
        = new AnnotationContext("attributeUse");
    public static final AnnotationContext WILDCARD
        = new AnnotationContext("wildcard");
    public static final AnnotationContext ATTRIBUTE_GROUP
        = new AnnotationContext("attributeGroup");
    public static final AnnotationContext ATTRIBUTE_DECL
        = new AnnotationContext("attributeDecl");
}
