/*
 * Copyright (c) 2003, 2004, Oracle and/or its affiliates. All rights reserved.
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

package sun.management.snmp.jvmmib;

//
// Generated by mibgen version 5.0 (06/02/03) when compiling JVM-MANAGEMENT-MIB in standard metadata mode.
//


// jmx imports
//
import com.sun.jmx.snmp.SnmpStatusException;

/**
 * This interface is used for representing the remote management interface for the "JvmRuntime" MBean.
 */
public interface JvmRuntimeMBean {

    /**
     * Getter for the "JvmRTBootClassPathSupport" variable.
     */
    public EnumJvmRTBootClassPathSupport getJvmRTBootClassPathSupport() throws SnmpStatusException;

    /**
     * Getter for the "JvmRTManagementSpecVersion" variable.
     */
    public String getJvmRTManagementSpecVersion() throws SnmpStatusException;

    /**
     * Getter for the "JvmRTSpecVersion" variable.
     */
    public String getJvmRTSpecVersion() throws SnmpStatusException;

    /**
     * Getter for the "JvmRTSpecVendor" variable.
     */
    public String getJvmRTSpecVendor() throws SnmpStatusException;

    /**
     * Getter for the "JvmRTSpecName" variable.
     */
    public String getJvmRTSpecName() throws SnmpStatusException;

    /**
     * Getter for the "JvmRTVMVersion" variable.
     */
    public String getJvmRTVMVersion() throws SnmpStatusException;

    /**
     * Getter for the "JvmRTVMVendor" variable.
     */
    public String getJvmRTVMVendor() throws SnmpStatusException;

    /**
     * Getter for the "JvmRTStartTimeMs" variable.
     */
    public Long getJvmRTStartTimeMs() throws SnmpStatusException;

    /**
     * Getter for the "JvmRTUptimeMs" variable.
     */
    public Long getJvmRTUptimeMs() throws SnmpStatusException;

    /**
     * Getter for the "JvmRTVMName" variable.
     */
    public String getJvmRTVMName() throws SnmpStatusException;

    /**
     * Getter for the "JvmRTName" variable.
     */
    public String getJvmRTName() throws SnmpStatusException;

    /**
     * Getter for the "JvmRTInputArgsCount" variable.
     */
    public Integer getJvmRTInputArgsCount() throws SnmpStatusException;

}
