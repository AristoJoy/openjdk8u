/*
 * Copyright (c) 1995, 2006, Oracle and/or its affiliates. All rights reserved.
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
 * This exception is raised if communication is lost while an operation
 * is in progress, after the request was sent by the client, but before
 * the reply from the server has been returned to the client.<P>
 * It contains a minor code, which gives more detailed information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 * <P>
 * See the section <A href="../../../../technotes/guides/idl/jidlExceptions.html#minorcodemeanings">meaning
 * of minor codes</A> to see the minor codes for this exception.
 *
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html#minorcodemeanings">meaning of
 * minor codes</A>
 * @since       JDK1.2
 */

public final class COMM_FAILURE extends SystemException {

    /**
     * Constructs a <code>COMM_FAILURE</code> exception with
     * a default minor code of 0 and a completion state of COMPLETED_NO.
     */
    public COMM_FAILURE() {
        this("");
    }

    /**
     * Constructs a <code>COMM_FAILURE</code> exception with the specified detail
     * message, a minor code of 0, and a completion state of COMPLETED_NO.
     *
     * @param s the <code>String</code> containing a detail message describing
     *          this exception
     */
    public COMM_FAILURE(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs a <code>COMM_FAILURE</code> exception with the specified
     * minor code and completion status.
     * @param minor the minor code
     * @param completed the completion status, which must be one of
     *                  <code>COMPLETED_YES</code>, <code>COMPLETED_NO</code>, or
     *                  <code>COMPLETED_MAYBE</code>.
     */
    public COMM_FAILURE(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs a <code>COMM_FAILURE</code> exception with the specified detail
     * message, minor code, and completion status.
     * A detail message is a String that describes this particular exception.
     * @param s the String containing a detail message
     * @param minor the minor code
     * @param completed the completion status, which must be one of
     *                  <code>COMPLETED_YES</code>, <code>COMPLETED_NO</code>, or
     *                  <code>COMPLETED_MAYBE</code>.
     */
    public COMM_FAILURE(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
