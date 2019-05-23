/*
 * Copyright 2019 Software Freedom Conservancy (SFC)
 * Modifications copyright (C) 2019 Inmarsat plc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.inmarsat.selenium;

import com.google.common.collect.ImmutableSet;

/**
 * <p>A static helper class concerned with providing common methods without context.</p>
 */
public class CapabilityHelper {

    private CapabilityHelper(){
        // EMPTY
    }

    /**
     * <p>Will check the object for strings that denote that any parameter will do, the following ways are
     * accepted:</p>
     * <ul>
     *     <li>""</li>
     *     <li>"any"</li>
     *     <li>"*"</li>
     * </ul>
     *
     * @param requested the {@link org.openqa.selenium.remote.DesiredCapabilities} to check.
     *
     * @return true if "any", "", or "*".
     */
    public static boolean anything(Object requested) {
        return requested == null ||
                ImmutableSet.of("any", "", "*").contains(requested.toString().toLowerCase());
    }

}
