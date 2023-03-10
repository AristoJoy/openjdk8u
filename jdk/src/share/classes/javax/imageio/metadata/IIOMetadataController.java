/*
 * Copyright (c) 2000, Oracle and/or its affiliates. All rights reserved.
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

package javax.imageio.metadata;

/**
 * An interface to be implemented by objects that can determine the
 * settings of an <code>IIOMetadata</code> object, either by putting
 * up a GUI to obtain values from a user, or by other means.  This
 * interface merely specifies a generic <code>activate</code> method
 * that invokes the controller, without regard for how the controller
 * obtains values (<i>i.e.</i>, whether the controller puts up a GUI
 * or merely computes a set of values is irrelevant to this
 * interface).
 *
 * <p> Within the <code>activate</code> method, a controller obtains
 * initial values by querying the <code>IIOMetadata</code> object's
 * settings, either using the XML DOM tree or a plug-in specific
 * interface, modifies values by whatever means, then modifies the
 * <code>IIOMetadata</code> object's settings, using either the
 * <code>setFromTree</code> or <code>mergeTree</code> methods, or a
 * plug-in specific interface.  In general, applications may expect
 * that when the <code>activate</code> method returns
 * <code>true</code>, the <code>IIOMetadata</code> object is ready for
 * use in a write operation.
 *
 * <p> Vendors may choose to provide GUIs for the
 * <code>IIOMetadata</code> subclasses they define for a particular
 * plug-in.  These can be set up as default controllers in the
 * corresponding <code>IIOMetadata</code> subclasses.
 *
 * <p> Alternatively, an algorithmic process such as a database lookup
 * or the parsing of a command line could be used as a controller, in
 * which case the <code>activate</code> method would simply look up or
 * compute the settings, call methods on <code>IIOMetadata</code> to
 * set its state, and return <code>true</code>.
 *
 * @see IIOMetadata#setController
 * @see IIOMetadata#getController
 * @see IIOMetadata#getDefaultController
 * @see IIOMetadata#hasController
 * @see IIOMetadata#activateController
 *
 */
public interface IIOMetadataController {

    /**
     * Activates the controller.  If <code>true</code> is returned,
     * all settings in the <code>IIOMetadata</code> object should be
     * ready for use in a write operation.  If <code>false</code> is
     * returned, no settings in the <code>IIOMetadata</code> object
     * will be disturbed (<i>i.e.</i>, the user canceled the
     * operation).
     *
     * @param metadata the <code>IIOMetadata</code> object to be modified.
     *
     * @return <code>true</code> if the <code>IIOMetadata</code> has been
     * modified, <code>false</code> otherwise.
     *
     * @exception IllegalArgumentException if <code>metadata</code> is
     * <code>null</code> or is not an instance of the correct class.
     */
    boolean activate(IIOMetadata metadata);
}
