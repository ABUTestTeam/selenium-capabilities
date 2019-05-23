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
import java.util.Objects;
import java.util.stream.Stream;

import static com.inmarsat.selenium.CapabilityHelper.anything;

/**
 * <p>When provided with one {@link #propertyAliases}, this will work the same as the
 * {@link SimplePropertyValidator}.</p>
 *
 * <p>When more than one {@link #propertyAliases} have been defined, then when matching capabilities
 * - if one of the properties match then the {@link #apply(Map, Map)} function will return true.</p>
 *
 * <p>If none of the properties in {@link #propertyAliases} match, then it will return false.</p>
 *
 * <p>This does not account for if you have set one of {@link #propertyAliases} to "any", then it will potentially
 * ignore other {@link #propertyAliases} defined depending on the order of definition.</p>
 */
public class AliasedPropertyValidator implements Validator {

    // The aliases to attempt to match against
    private final String[] propertyAliases;

    /**
     * @param propertyAliases the "keys" to attempt to match in the order that they are defined by implementation.
     */
    public AliasedPropertyValidator(String... propertyAliases){
        this.propertyAliases = propertyAliases;
    }

    @Override
    public Boolean apply(Map<String, Object> providedCapabilities, Map<String, Object> desiredCapabilities) {

        Object requested = Stream.of(propertyAliases)
                .map(desiredCapabilities::get)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        if (anything(requested)) {
            return true;
        }

        Object provided = Stream.of(propertyAliases)
                .map(providedCapabilities::get)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        return Objects.equals(requested, provided);
    }
}
