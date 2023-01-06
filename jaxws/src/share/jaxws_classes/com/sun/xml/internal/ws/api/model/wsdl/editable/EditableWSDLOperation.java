/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.xml.internal.ws.api.model.wsdl.editable;

import javax.xml.namespace.QName;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOperation;

public interface EditableWSDLOperation extends WSDLOperation {

    @Override
    @NotNull
    EditableWSDLInput getInput();

    /**
     * Set input
     *
     * @param input Input
     */
    public void setInput(EditableWSDLInput input);

    @Override
    @Nullable
    EditableWSDLOutput getOutput();

    /**
     * Set output
     *
     * @param output Output
     */
    public void setOutput(EditableWSDLOutput output);

    @Override
    Iterable<? extends EditableWSDLFault> getFaults();

    /**
     * Add fault
     *
     * @param fault Fault
     */
    public void addFault(EditableWSDLFault fault);

    @Override
    @Nullable
    EditableWSDLFault getFault(QName faultDetailName);

    /**
     * Set parameter order
     *
     * @param parameterOrder Parameter order
     */
    public void setParameterOrder(String parameterOrder);

    /**
     * Freezes WSDL model to prevent further modification
     *
     * @param root WSDL Model
     */
    public void freeze(EditableWSDLModel root);
}
