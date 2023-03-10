/*
 * Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
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

#import <JavaNativeFoundation/JavaNativeFoundation.h>

extern NSString *const JavaAccessibilityIgnore;

extern NSMutableDictionary *sRoles;
extern void initializeRoles();

extern JNFClassInfo sjc_CAccessibility;
extern JNFClassInfo sjc_AccessibleComponent;
extern JNFClassInfo sjc_AccessibleContext;
extern JNFClassInfo sjc_Accessible;
extern JNFClassInfo sjc_AccessibleRole;
extern JNFClassInfo sjc_Point;
extern JNFClassInfo sjc_AccessibleText;

extern JNFMemberInfo *sjm_getAccessibleRole;
extern JNFMemberInfo *sjf_key;
extern JNFMemberInfo *sjf_X;
extern JNFMemberInfo *sjf_Y;

NSSize getAxComponentSize(JNIEnv *env, jobject axComponent, jobject component);
NSString *getJavaRole(JNIEnv *env, jobject axComponent, jobject component);
jobject getAxSelection(JNIEnv *env, jobject axContext, jobject component);
jobject getAxContextSelection(JNIEnv *env, jobject axContext, jint index, jobject component);
void setAxContextSelection(JNIEnv *env, jobject axContext, jint index, jobject component);
jobject getAxContext(JNIEnv *env, jobject accessible, jobject component);
BOOL isChildSelected(JNIEnv *env, jobject accessible, jint index, jobject component);
jobject getAxStateSet(JNIEnv *env, jobject axContext, jobject component);
BOOL containsAxState(JNIEnv *env, jobject axContext, jobject axState, jobject component);
BOOL isVertical(JNIEnv *env, jobject axContext, jobject component);
BOOL isHorizontal(JNIEnv *env, jobject axContext, jobject component);
BOOL isShowing(JNIEnv *env, jobject axContext, jobject component);
NSPoint getAxComponentLocationOnScreen(JNIEnv *env, jobject axComponent, jobject component);
jint getAxTextCharCount(JNIEnv *env, jobject axText, jobject component);

// these methods are copied from the corresponding NSAccessibility methods
id JavaAccessibilityAttributeValue(id element, NSString *attribute);
BOOL JavaAccessibilityIsAttributeSettable(id element, NSString *attribute);
void JavaAccessibilitySetAttributeValue(id element, NSString *attribute, id value);

// these methods are copied from the corresponding NSAccessibilityErrors methods
void JavaAccessibilityRaiseSetAttributeToIllegalTypeException(const char *functionName, id element, NSString *attribute, id value);
void JavaAccessibilityRaiseUnimplementedAttributeException(const char *functionName, id element, NSString *attribute);
void JavaAccessibilityRaiseIllegalParameterTypeException(const char *functionName, id element, NSString *attribute, id parameter);
