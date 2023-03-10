/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.xml.internal.ws.server;

import com.sun.istack.internal.NotNull;
import com.sun.xml.internal.ws.api.message.Packet;
import com.sun.xml.internal.ws.api.server.AbstractInstanceResolver;
import com.sun.xml.internal.ws.api.server.InstanceResolver;
import com.sun.xml.internal.ws.api.server.WSEndpoint;
import com.sun.xml.internal.ws.api.server.WSWebServiceContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * {@link InstanceResolver} that always returns a single instance.
 *
 * @author Kohsuke Kawaguchi
 */
public final class SingletonResolver<T> extends AbstractInstanceResolver<T> {
    private final @NotNull T singleton;

    public SingletonResolver(@NotNull T singleton) {
        this.singleton = singleton;
    }

    public @NotNull T resolve(Packet request) {
        return singleton;
    }

    public void start(WSWebServiceContext wsc, WSEndpoint endpoint) {
        getResourceInjector(endpoint).inject(wsc,singleton);
        // notify that we are ready to serve
        invokeMethod(findAnnotatedMethod(singleton.getClass(),PostConstruct.class),singleton);
    }

    public void dispose() {
        invokeMethod(findAnnotatedMethod(singleton.getClass(),PreDestroy.class),singleton);
    }
}
