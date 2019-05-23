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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.inmarsat.selenium.CapabilityHelper.anything;

/**
 * <p>Simple object comparison between things to consider.</p>
 *
 * <p>Has the following filters:</p>
 * <ul>
 *     <li>1. Will not consider things starting with the Grid Token "_"</li>
 *     <li>2. Will only consider things within the {@link #toConsider} list</li>
 *     <li>3. Will pass through anything that is set to any</li>
 * </ul>
 *
 * <p>It will compare anything directly that has passed through the filters above.</p>
 */
public class SimplePropertyValidator implements Validator {

    private static final String GRID_TOKEN = "_";

    private final List<String> toConsider;

    /**
     * @param toConsider A list of tokens to consider for comparison.
     */
    public SimplePropertyValidator(String... toConsider) {
        this.toConsider = Arrays.asList(toConsider);
    }

    @Override
    public Boolean apply(Map<String, Object> providedCapabilities, Map<String, Object> requestedCapabilities) {
        return requestedCapabilities.entrySet().stream()
                .filter(entry -> ! entry.getKey().startsWith(GRID_TOKEN))
                .filter(entry -> toConsider.contains(entry.getKey()))
                .filter(entry -> ! anything(entry.getValue()))
                .allMatch(entry -> entry.getValue().equals(providedCapabilities.get(entry.getKey())));
    }
}
