/**
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.codehaus.groovy.classgen;

import groovy.util.GroovyTestCase;

/**
 * @author <a href="mailto:james@coredevelopers.net">James Strachan</a>
 */
public class CapitalizeTest extends GroovyTestCase {

    public void testCapitalize() {
        assertEquals("Foo", Verifier.capitalize("foo"));
        assertEquals("Foo", Verifier.capitalize("Foo"));
        assertEquals("fOo", Verifier.capitalize("fOo"));
        assertEquals("fOO", Verifier.capitalize("fOO"));
        assertEquals("F", Verifier.capitalize("f"));
    }

}
