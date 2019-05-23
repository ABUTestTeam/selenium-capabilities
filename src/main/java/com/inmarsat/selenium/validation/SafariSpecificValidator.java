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

import org.openqa.selenium.ImmutableCapabilities;
import org.openqa.selenium.safari.SafariOptions;

import java.util.Map;

import static org.openqa.selenium.remote.CapabilityType.BROWSER_NAME;

/**
 * <p>Compared specific options from the {@link SafariOptions} specifically for the
 * {@link org.openqa.selenium.safari.SafariDriver}. It compares the following:</p>
 * <ul>
 *     <li>Automatic Inspection</li>
 *     <li>Automatic Profiling</li>
 *     <li>Use Technology Preview</li>
 * </ul>
 *
 * <p>In the {@link org.openqa.selenium.remote.CapabilityType#BROWSER_NAME} is not "safari" or
 * "Safari Technology Preview" then it will return true.</p>
 */
public class SafariSpecificValidator implements Validator {

    @Override
    public Boolean apply(Map<String, Object> providedCapabilities, Map<String, Object> desiredCapabilities) {

        if (!"safari".equals(desiredCapabilities.get(BROWSER_NAME)) &&
                !"Safari Technology Preview".equals(desiredCapabilities.get(BROWSER_NAME))) {
            return true;
        }

        SafariOptions providedOptions = new SafariOptions(new ImmutableCapabilities(providedCapabilities));
        SafariOptions requestedOptions = new SafariOptions(new ImmutableCapabilities(desiredCapabilities));

        return requestedOptions.getAutomaticInspection() == providedOptions.getAutomaticInspection() &&
                requestedOptions.getAutomaticProfiling() == providedOptions.getAutomaticProfiling() &&
                requestedOptions.getUseTechnologyPreview() == providedOptions.getUseTechnologyPreview();
    }
}
