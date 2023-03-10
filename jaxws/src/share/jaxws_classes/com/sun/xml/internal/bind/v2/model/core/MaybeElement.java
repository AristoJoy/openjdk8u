/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.xml.internal.bind.v2.model.core;

import javax.xml.namespace.QName;

/**
 * Some {@link NonElement} can optionally be an {@link Element}.
 *
 * This interface captures such characteristics.
 *
 * @author Kohsuke Kawaguchi
 */
public interface MaybeElement<T,C> extends NonElement<T,C> {
    /**
     * If the class is bound to an element, return true.
     *
     * <p>
     * Note that when this is true, the class is bound to both an element
     * and a type.
     */
    boolean isElement();

    /**
     * Gets the element name of the class, if the class is bound
     * to an element.
     *
     * @return
     *      non-null iff {@link #isElement()}.
     */
    QName getElementName();

    /**
     * Returns the {@link Element} aspect of this {@link ClassInfo}.
     *
     * @return
     *      null if {@link #isElement()}==false, non-null if {@link #isElement()}==true.
     */
    Element<T,C> asElement();
}
