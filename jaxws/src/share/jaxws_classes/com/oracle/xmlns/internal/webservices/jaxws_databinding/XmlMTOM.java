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
import javax.xml.ws.soap.MTOM;
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
 *       &lt;attribute name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="threshold" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "mtom")
public class XmlMTOM implements MTOM {

    @XmlAttribute(name = "enabled")
    protected Boolean enabled;
    @XmlAttribute(name = "threshold")
    protected Integer threshold;

    /**
     * Gets the value of the enabled property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public boolean isEnabled() {
        if (enabled == null) {
            return true;
        } else {
            return enabled;
        }
    }

    /**
     * Sets the value of the enabled property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

    /**
     * Gets the value of the threshold property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public int getThreshold() {
        if (threshold == null) {
            return  0;
        } else {
            return threshold;
        }
    }

    /**
     * Sets the value of the threshold property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setThreshold(Integer value) {
        this.threshold = value;
    }

    @Override
    public boolean enabled() {
        return nullSafe(enabled, Boolean.TRUE);
    }

    @Override
    public int threshold() {
        return nullSafe(threshold, 0);
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return MTOM.class;
    }
}
