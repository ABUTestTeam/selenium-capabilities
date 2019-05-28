/*
 * Copyright 2019 Inmarsat plc.
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

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.CapabilityType;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static com.inmarsat.selenium.CapabilityHelper.anything;
import static com.inmarsat.selenium.validation.PlatformValidator.extractPlatform;

public class AppiumPropertyValidator implements Validator{

    private final String[] propertyAliases;

    public AppiumPropertyValidator(String... propertyAliases){
        this.propertyAliases = propertyAliases;
    }


    @Override
    public Boolean apply(Map<String, Object> providedCapabilities, Map<String, Object> desiredCapabilities) {

        // Filter for appium only nodes (Android and IOS)
        if(providedCapabilities.get(CapabilityType.PLATFORM_NAME) == null
            || (!extractPlatform(providedCapabilities.get(CapabilityType.PLATFORM_NAME)).is(Platform.ANDROID)
            && !extractPlatform(providedCapabilities.get(CapabilityType.PLATFORM_NAME)).is(Platform.IOS))) {

            return true;
        }

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
