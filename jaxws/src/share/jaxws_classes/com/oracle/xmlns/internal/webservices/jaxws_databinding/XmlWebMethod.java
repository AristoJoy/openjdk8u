/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
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

package com.oracle.xmlns.internal.webservices.jaxws_databinding;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.lang.annotation.Annotation;

import static com.oracle.xmlns.internal.webservices.jaxws_databinding.Util.nullSafe;


/**
 * This file was generated by JAXB-RI v2.2.6 and afterwards modified
 * to implement appropriate Annotation
 *
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="action" type="{http://www.w3.org/2001/XMLSchema}string" default="" />
 *       &lt;attribute name="exclude" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="operation-name" type="{http://www.w3.org/2001/XMLSchema}string" default="" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "web-method")
public class XmlWebMethod implements javax.jws.WebMethod {

    @XmlAttribute(name = "action")
    protected String action;
    @XmlAttribute(name = "exclude")
    protected Boolean exclude;
    @XmlAttribute(name = "operation-name")
    protected String operationName;

    /**
     * Gets the value of the action property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAction() {
        if (action == null) {
            return "";
        } else {
            return action;
        }
    }

    /**
     * Sets the value of the action property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAction(String value) {
        this.action = value;
    }

    /**
     * Gets the value of the exclude property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public boolean isExclude() {
        if (exclude == null) {
            return false;
        } else {
            return exclude;
        }
    }

    /**
     * Sets the value of the exclude property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setExclude(Boolean value) {
        this.exclude = value;
    }

    /**
     * Gets the value of the operationName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getOperationName() {
        if (operationName == null) {
            return "";
        } else {
            return operationName;
        }
    }

    /**
     * Sets the value of the operationName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setOperationName(String value) {
        this.operationName = value;
    }

    @Override
    public String operationName() {
        return nullSafe(operationName);
    }

    @Override
    public String action() {
        return nullSafe(action);
    }

    @Override
    public boolean exclude() {
        return nullSafe(exclude, false);
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return javax.jws.WebMethod.class;
    }
}
