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
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.grid.internal.utils.CapabilityMatcher;
import org.openqa.selenium.remote.CapabilityType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.openqa.selenium.remote.CapabilityType.BROWSER_NAME;

/**
 * <p>A capabilities matcher based on the {@link org.openqa.grid.internal.utils.DefaultCapabilityMatcher}
 * implementation by seleniumHQ. It compares the {@link org.openqa.selenium.Capabilities} of the grid to
 * the {@link org.openqa.selenium.remote.DesiredCapabilities} requested. If the capabilities of both match
 * then it returns true and the node information will be returned by grid.</p>
 *
 * <p>Things are currently case sensitive.</p>
 *
 * <p>The {@link org.openqa.grid.internal.utils.DefaultCapabilityMatcher} and hence this class follow the
 * {@link Validator} pattern. The following validators are registered in this capabilities matcher:</p>
 *
 * <ul>
 *     <li>{@link AliasedPropertyValidator#AliasedPropertyValidator(String...)} {@link CapabilityType#BROWSER_NAME}</li>
 *     <li>{@link VersionValidator#VersionValidator(String)} {@link CapabilityType#BROWSER_VERSION}</li>
 *     <li>{@link FirefoxSpecificValidator}</li>
 *     <li>{@link PlatformValidator}</li>
 *     <li>{@link VersionValidator#VersionValidator(String)} {@link CapabilityType#VERSION}</li>
 *     <li>{@link SafariSpecificValidator}</li>
 *     <li>{@link SimplePropertyValidator#SimplePropertyValidator(String...)}</li>
 * </ul>
 */
public class CustomCapabilitiesMatcher implements CapabilityMatcher {

    private final List<Validator> validators = new ArrayList<>();
    {
        validators.addAll(Arrays.asList(
            new AliasedPropertyValidator(BROWSER_NAME, "browser"),
            new VersionValidator(CapabilityType.BROWSER_VERSION),
            new FirefoxSpecificValidator(),
            new PlatformValidator(),
            new VersionValidator(CapabilityType.VERSION),
            new VersionValidator(MobileCapabilityType.PLATFORM_VERSION),
            new SafariSpecificValidator(),
            new SimplePropertyValidator(CapabilityType.APPLICATION_NAME),
            new AppiumPropertyValidator(MobileCapabilityType.UDID)
        ));
    }

    /**
     * ENTRY POINT
     *
     * <p>This is the called function by the Grid.</p>
     *
     * @param providedCapabilities the {@link org.openqa.selenium.Capabilities} provided by the grid
     * @param requestedCapabilities  the {@link org.openqa.selenium.remote.DesiredCapabilities} provided externally
     *
     * @return true if matches, false if not, will work off first one. The provided and requested capabilities cannot
     * be null.
     */
    @Override
    public boolean matches(Map<String, Object> providedCapabilities, Map<String, Object> requestedCapabilities) {

        return providedCapabilities != null && requestedCapabilities != null
                && validators.stream().allMatch(v -> v.apply(providedCapabilities, requestedCapabilities));
    }
}