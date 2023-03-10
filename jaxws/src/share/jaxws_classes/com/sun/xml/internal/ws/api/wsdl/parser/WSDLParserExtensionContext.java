/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.xml.internal.ws.api.wsdl.parser;

import com.sun.istack.internal.NotNull;
import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLModel;
import com.sun.xml.internal.ws.api.server.Container;
import com.sun.xml.internal.ws.api.policy.PolicyResolver;

/**
 * Provides contextual information for {@link WSDLParserExtension}s.
 *
 * @author Vivek Pandey
 * @author Fabian Ritzmann
 */
public interface WSDLParserExtensionContext {
    /**
     * Returns true if the WSDL parsing is happening on the client side. Returns false means
     * its started on the server side.
     */
    boolean isClientSide();

    /**
     * Gives the {@link EditableWSDLModel}. The WSDLModel may not be complete until
     * {@link WSDLParserExtension#finished(WSDLParserExtensionContext)} is called.
     */
    EditableWSDLModel getWSDLModel();

    /**
     * Provides the {@link Container} in which this service or client is running.
     * May return null.
     *
     * @return The container in which this service or client is running.
     */
    @NotNull Container getContainer();


    /**
     * Provides the PolicyResolver
     */
    @NotNull
    PolicyResolver getPolicyResolver();
}
