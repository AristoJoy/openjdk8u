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

#import "JavaComponentAccessibility.h"

#import <AppKit/NSAccessibility.h>


@interface JavaTextAccessibility : JavaComponentAccessibility {

}
// attributes
- (NSArray *)initializeAttributeNamesWithEnv:(JNIEnv *)env;
- (NSString *)accessibilityValueAttribute;
- (BOOL)accessibilityIsValueAttributeSettable;
- (void)accessibilitySetValueAttribute:(id)value;
- (NSString *)accessibilitySelectedTextAttribute;
- (BOOL)accessibilityIsSelectedTextAttributeSettable;
- (NSValue *)accessibilitySelectedTextRangeAttribute;
- (BOOL)accessibilityIsSelectedTextRangeAttributeSettable;
- (NSNumber *)accessibilityNumberOfCharactersAttribute;
- (BOOL)accessibilityIsNumberOfCharactersAttributeSettable;
- (NSValue *)accessibilityVisibleCharacterRangeAttribute;
- (BOOL)accessibilityIsVisibleCharacterRangeAttributeSettable;
- (NSValue *)accessibilityInsertionPointLineNumberAttribute;
- (BOOL)accessibilityIsInsertionPointLineNumberAttributeSettable;
- (void)accessibilitySetSelectedTextAttribute:(id)value;
- (NSValue *)accessibilitySelectedTextRangeAttribute;
- (NSValue *)accessibilityInsertionPointLineNumberAttribute;
- (BOOL)accessibilityIsInsertionPointLineNumberAttributeSettable;

// parameterized attributes
- (NSArray *)accessibilityParameterizedAttributeNames;
- (NSValue *)accessibilityBoundsForRangeAttributeForParameter:(id)parameter;
- (NSNumber *)accessibilityLineForIndexAttributeForParameter:(id)parameter;
- (NSValue *)accessibilityRangeForLineAttributeForParameter:(id)parameter;
- (NSString *)accessibilityStringForRangeAttributeForParameter:(id)parameter;
- (NSValue *)accessibilityRangeForPositionAttributeForParameter:(id)parameter;
- (NSValue *)accessibilityRangeForIndexAttributeForParameter:(id)parameter;

// actions
- (NSDictionary *)getActions:(JNIEnv *)env;
@end
