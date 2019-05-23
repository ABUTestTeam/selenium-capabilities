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

import com.inmarsat.selenium.validation.*;
import org.openqa.grid.internal.utils.CapabilityMatcher;
import org.openqa.selenium.remote.CapabilityType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.openqa.selenium.remote.CapabilityType.BROWSER_NAME;

/**
 * Selenium Grid uses this class to match DesiredCapabilities to ActualCapabilities
 *
 * Can either extend DefaultCapabilityMatcher or implement CapabilityMather
 *
 * Sub-interface Validator (extends BiFunction\<Map, Map\>) could be useful as it ensures you call Apply
 *
 * In DefaultCapabilityMatcher there is an anything(requested) value for genericity, could use...
 * if(anything(requested)) return true;
 *
 * Currently in DefaultCapabilityMatcher you have the following:
 * <ul>
 *     <li>PlatformValidator</li>
 *     <li>AliasedPropertyValidator(Browser)</li>
 *     <li>AliasedPropertyValidator(Version)</li>
 *     <li>SimplePropertyValidator(App Name)</li>
 *     <li>Firefox validator</li>
 *     <li>Safari validator</li>
 * </ul>
 *
 * They are implementing validators (private final),,, rebuild based on work done by selenium
 *
 */
public class CustomCapabilitiesMatcher implements CapabilityMatcher {

    private final List<Validator> validators = new ArrayList<>();
    {
        validators.addAll(Arrays.asList(
            new AliasedPropertyValidator(BROWSER_NAME, "browser"),
            new AliasedPropertyValidator(CapabilityType.BROWSER_VERSION, CapabilityType.VERSION),
            new FirefoxSpecificValidator(),
            new PlatformValidator(),
            new SafariSpecificValidator(),
            new SimplePropertyValidator(CapabilityType.APPLICATION_NAME)
        ));
    }

    /**
     * This is the called function by the Grid.
     *
     * @param providedCapabilities the Capabilities provided by the grid
     * @param requestedCapabilities  the DesiredCapabilities provided by the framework
     * @return true if matches, false if not, will work off first one.
     */
    @Override
    public boolean matches(Map<String, Object> providedCapabilities, Map<String, Object> requestedCapabilities) {
        return providedCapabilities != null && requestedCapabilities != null
                && validators.stream().allMatch(v -> v.apply(providedCapabilities, requestedCapabilities));
    }

}