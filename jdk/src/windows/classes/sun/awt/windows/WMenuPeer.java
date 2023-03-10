/*
 * Copyright (c) 1996, 2005, Oracle and/or its affiliates. All rights reserved.
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
package sun.awt.windows;

import java.awt.*;
import java.awt.peer.*;

class WMenuPeer extends WMenuItemPeer implements MenuPeer {

    // MenuPeer implementation

    public native void addSeparator();
    public void addItem(MenuItem item) {
        WMenuItemPeer itemPeer = (WMenuItemPeer) WToolkit.targetToPeer(item);
    }
    public native void delItem(int index);

    // Toolkit & peer internals

    WMenuPeer() {}   // used by subclasses.

    WMenuPeer(Menu target) {
        this.target = target;
        MenuContainer parent = target.getParent();

        if (parent instanceof MenuBar) {
            WMenuBarPeer mbPeer = (WMenuBarPeer) WToolkit.targetToPeer(parent);
            this.parent = mbPeer;
            createMenu(mbPeer);
        }
        else if (parent instanceof Menu) {
            this.parent = (WMenuPeer) WToolkit.targetToPeer(parent);
            createSubMenu(this.parent);
        }
        else {
            throw new IllegalArgumentException("unknown menu container class");
        }
        // fix for 5088782: check if menu object is created successfully
        checkMenuCreation();
    }

    native void createMenu(WMenuBarPeer parent);
    native void createSubMenu(WMenuPeer parent);
}
