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
package groovy.lang;

import java.util.List;

/**
 * Represents a sequence of objects which represents one or many instances of
 * of objects of a given type. The type can be omitted in which case any type of
 * object can be added.
 *
 * @author <a href="mailto:james@coredevelopers.net">James Strachan</a>
 */
public class NonEmptySequence extends Sequence {

    public NonEmptySequence() {
        super(null);
    }

    public NonEmptySequence(Class type) {
        super(type);
    }

    public NonEmptySequence(Class type, List content) {
        super(type, content);
    }

    public int minimumSize() {
        return 1;
    }
}