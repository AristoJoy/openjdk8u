/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.invoke;

/**
 * LambdaConversionException
 */
public class LambdaConversionException extends Exception {
    private static final long serialVersionUID = 292L + 8L;

    /**
     * Constructs a {@code LambdaConversionException}.
     */
    public LambdaConversionException() {
    }

    /**
     * Constructs a {@code LambdaConversionException} with a message.
     * @param message the detail message
     */
    public LambdaConversionException(String message) {
        super(message);
    }

    /**
     * Constructs a {@code LambdaConversionException} with a message and cause.
     * @param message the detail message
     * @param cause the cause
     */
    public LambdaConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a {@code LambdaConversionException} with a cause.
     * @param cause the cause
     */
    public LambdaConversionException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a {@code LambdaConversionException} with a message,
     * cause, and other settings.
     * @param message the detail message
     * @param cause the cause
     * @param enableSuppression whether or not suppressed exceptions are enabled
     * @param writableStackTrace whether or not the stack trace is writable
     */
    public LambdaConversionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
