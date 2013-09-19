/*
 * Copyright 2003-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package groovy.bugs

import org.codehaus.groovy.ast.ClassHelper

class Groovy3716Bug extends GroovyTestCase {
    void testVoidAndObjectDerivedFromResults() {
        assertTrue ClassHelper.VOID_TYPE.isDerivedFrom(ClassHelper.VOID_TYPE)
        assertFalse ClassHelper.OBJECT_TYPE.isDerivedFrom(ClassHelper.VOID_TYPE)
        assertFalse ClassHelper.VOID_TYPE.isDerivedFrom(ClassHelper.OBJECT_TYPE)        
    }
}
