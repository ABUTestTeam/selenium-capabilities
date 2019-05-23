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
package com.inmarsat.selenium.validation;

import java.util.Map;
import java.util.function.BiFunction;

/**
 * <p>To fit the validator pattern implemented in {@link com.inmarsat.selenium.CustomCapabilitiesMatcher},
 * all validation methods much implement the {@link #apply(Map, Map)} function.</p>
 */
public interface Validator extends BiFunction<Map<String, Object>, Map<String, Object>, Boolean> {

    /**
     * <p>This function originates from {@link BiFunction}.</p>
     *
     * @param providedCapabilities The {@link org.openqa.selenium.Capabilities} of each of the nodes on the Grid where
     *                             the capability matcher is running.
     * @param desiredCapabilities The {@link org.openqa.selenium.remote.DesiredCapabilities} sent to the grid
     *
     * @return true if the providedCapabilities match the desiredCapabilities.
     */
    @Override
    Boolean apply(Map<String, Object> providedCapabilities, Map<String, Object> desiredCapabilities);

}
