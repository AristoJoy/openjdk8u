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
 *       &lt;attribute name="endpoint-interface" type="{http://www.w3.org/2001/XMLSchema}string" default="" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" default="" />
 *       &lt;attribute name="port-name" type="{http://www.w3.org/2001/XMLSchema}string" default="" />
 *       &lt;attribute name="service-name" type="{http://www.w3.org/2001/XMLSchema}string" default="" />
 *       &lt;attribute name="target-namespace" type="{http://www.w3.org/2001/XMLSchema}string" default="" />
 *       &lt;attribute name="wsdl-location" type="{http://www.w3.org/2001/XMLSchema}string" default="" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "web-service")
public class XmlWebService implements javax.jws.WebService {

    @XmlAttribute(name = "endpoint-interface")
    protected String endpointInterface;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "port-name")
    protected String portName;
    @XmlAttribute(name = "service-name")
    protected String serviceName;
    @XmlAttribute(name = "target-namespace")
    protected String targetNamespace;
    @XmlAttribute(name = "wsdl-location")
    protected String wsdlLocation;

    /**
     * Gets the value of the endpointInterface property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getEndpointInterface() {
        if (endpointInterface == null) {
            return "";
        } else {
            return endpointInterface;
        }
    }

    /**
     * Sets the value of the endpointInterface property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setEndpointInterface(String value) {
        this.endpointInterface = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getName() {
        if (name == null) {
            return "";
        } else {
            return name;
        }
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the portName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPortName() {
        if (portName == null) {
            return "";
        } else {
            return portName;
        }
    }

    /**
     * Sets the value of the portName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPortName(String value) {
        this.portName = value;
    }

    /**
     * Gets the value of the serviceName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getServiceName() {
        if (serviceName == null) {
            return "";
        } else {
            return serviceName;
        }
    }

    /**
     * Sets the value of the serviceName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setServiceName(String value) {
        this.serviceName = value;
    }

    /**
     * Gets the value of the targetNamespace property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTargetNamespace() {
        if (targetNamespace == null) {
            return "";
        } else {
            return targetNamespace;
        }
    }

    /**
     * Sets the value of the targetNamespace property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTargetNamespace(String value) {
        this.targetNamespace = value;
    }

    /**
     * Gets the value of the wsdlLocation property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getWsdlLocation() {
        if (wsdlLocation == null) {
            return "";
        } else {
            return wsdlLocation;
        }
    }

    /**
     * Sets the value of the wsdlLocation property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setWsdlLocation(String value) {
        this.wsdlLocation = value;
    }

    @Override
    public String name() {
        return nullSafe(name);
    }

    @Override
    public String targetNamespace() {
        return nullSafe(targetNamespace);
    }

    @Override
    public String serviceName() {
        return nullSafe(serviceName);
    }

    @Override
    public String portName() {
        return nullSafe(portName);
    }

    @Override
    public String wsdlLocation() {
        return nullSafe(wsdlLocation);
    }

    @Override
    public String endpointInterface() {
        return nullSafe(endpointInterface);
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return javax.jws.WebService.class;
    }
}
