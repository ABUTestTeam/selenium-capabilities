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

import static org.openqa.selenium.remote.CapabilityType.BROWSER_NAME;

/**
 * <p>Checks the "marionette" property, and matches to the boolean value. Specific option within the
 * {@link org.openqa.selenium.firefox.FirefoxOptions} and {@link org.openqa.selenium.firefox.FirefoxDriver}.</p>
 *
 * <p>If {@link org.openqa.selenium.remote.CapabilityType#BROWSER_NAME} is not firefox, returns true by default.</p>
 */
public class FirefoxSpecificValidator implements Validator {

    private static final String MARIONETTE = "marionette";

    @Override
    public Boolean apply(Map<String, Object> providedCapabilities, Map<String, Object> desiredCapabilities) {

        if (! "firefox".equals(desiredCapabilities.get(BROWSER_NAME))) {
            return true;
        }

        if (desiredCapabilities.get(MARIONETTE) != null &&
                !Boolean.valueOf(desiredCapabilities.get(MARIONETTE).toString())) {

            return providedCapabilities.get(MARIONETTE) != null &&
                    !Boolean.valueOf(providedCapabilities.get(MARIONETTE).toString());
        } else {

            return providedCapabilities.get(MARIONETTE) == null ||
                    Boolean.valueOf(providedCapabilities.get(MARIONETTE).toString());
        }
    }
}
