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

import com.inmarsat.selenium.CapabilityHelper;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.CapabilityType;

import java.util.Map;
import java.util.Objects;

/**
 * <p>Compares the platform fields in the provided and desired capabilities. This can match the platform version if
 * defined within the platform field (i.e. platform=Win7.</p>
 *
 * <p>The {@link #extractPlatform(Object)} extracts the core platform from the provided string. See {@link Platform}
 *  for more details about how this is done.</p>
 *
 * <br>
 *
 * <p>NOTE: The implementation of this will need to be changed with 4.0.0 when PLATFORM is removed from grid. It
 * will be replaces with PLATFORM_NAME.</p>
 */
public class PlatformValidator implements Validator {

    @Override
    public Boolean apply(Map<String, Object> providedCapabilities, Map<String, Object> desiredCapabilities) {

        Object provided = providedCapabilities.get(CapabilityType.PLATFORM_NAME);

        if (CapabilityHelper.anything(desiredCapabilities.get(CapabilityType.PLATFORM_NAME)) ||
                desiredCapabilities.get(CapabilityType.PLATFORM_NAME) == null) {
            return true;
        }

        Object desired = desiredCapabilities.get(CapabilityType.PLATFORM_NAME);

        Platform desiredPlatform = extractPlatform(desiredCapabilities.get(CapabilityType.PLATFORM_NAME));

        if (desiredPlatform != null) {

            Platform providedPlatform = extractPlatform(providedCapabilities.get(CapabilityType.PLATFORM_NAME));

            return providedPlatform != null && providedPlatform.is(desiredPlatform);
        }

        return provided != null && Objects.equals(desired.toString(), provided.toString());
    }

    /**
     * <p>Extracts the platform string from {@link Platform#fromString(String)} to make it comparable.</p>
     *
     * @param o the object to extract from, given by either the {@link org.openqa.selenium.Capabilities} or
     *          {@link org.openqa.selenium.remote.DesiredCapabilities}.
     *
     * @return the parsed version of the {@link Platform} for comparison.
     */
     static Platform extractPlatform(Object o) {

        if (o == null) {
            return null;
        }

        if (o instanceof Platform) {
            return (Platform) o;
        }

        try {
            return Platform.fromString(o.toString());
        } catch (WebDriverException ex) {
            return null;
        }
    }
}
