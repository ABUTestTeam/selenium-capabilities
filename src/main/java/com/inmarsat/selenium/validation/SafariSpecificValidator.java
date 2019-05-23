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

public class SafariSpecificValidator implements Validator {

    @Override
    public Boolean apply(Map<String, Object> providedCapabilities, Map<String, Object> requestedCapabilities) {
        if (!"safari".equals(requestedCapabilities.get(BROWSER_NAME)) &&
                !"Safari Technology Preview".equals(requestedCapabilities.get(BROWSER_NAME))) {
            return true;
        }

        SafariOptions providedOptions = new SafariOptions(new ImmutableCapabilities(providedCapabilities));
        SafariOptions requestedOptions = new SafariOptions(new ImmutableCapabilities(requestedCapabilities));

        return requestedOptions.getAutomaticInspection() == providedOptions.getAutomaticInspection() &&
                requestedOptions.getAutomaticProfiling() == providedOptions.getAutomaticProfiling() &&
                requestedOptions.getUseTechnologyPreview() == providedOptions.getUseTechnologyPreview();
    }
}
