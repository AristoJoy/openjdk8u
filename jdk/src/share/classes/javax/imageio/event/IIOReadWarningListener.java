/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.imageio.event;

import java.util.EventListener;
import javax.imageio.ImageReader;

/**
 * An interface used by <code>ImageReader</code> implementations to
 * notify callers of their image and thumbnail reading methods of
 * warnings (non-fatal errors).  Fatal errors cause the relevant
 * read method to throw an <code>IIOException</code>.
 *
 * <p> Localization is handled by associating a <code>Locale</code>
 * with each <code>IIOReadWarningListener</code> as it is registered
 * with an <code>ImageReader</code>.  It is up to the
 * <code>ImageReader</code> to provide localized messages.
 *
 * @see javax.imageio.ImageReader#addIIOReadWarningListener
 * @see javax.imageio.ImageReader#removeIIOReadWarningListener
 *
 */
public interface IIOReadWarningListener extends EventListener {

    /**
     * Reports the occurrence of a non-fatal error in decoding.  Decoding
     * will continue following the call to this method.  The application
     * may choose to display a dialog, print the warning to the console,
     * ignore the warning, or take any other action it chooses.
     *
     * @param source the <code>ImageReader</code> object calling this method.
     * @param warning a <code>String</code> containing the warning.
     */
    void warningOccurred(ImageReader source, String warning);
}
