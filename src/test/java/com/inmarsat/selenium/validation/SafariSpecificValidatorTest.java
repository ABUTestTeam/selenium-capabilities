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

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class SafariSpecificValidatorTest extends AbstractValidatorTest {

    @BeforeMethod
    public void setUp(){

        setUpValidatorTest();
        validator = new SafariSpecificValidator();
    }

    @Test
    public void shouldMatchSafariTechnologyPreviewOnly() {
        Map<String, Object> requested = new SafariOptions().setUseTechnologyPreview(true).asMap();

        Map<String, Object> tpNode = new HashMap<>();
        tpNode.put(CapabilityType.BROWSER_NAME, "Safari Technology Preview");
        tpNode.put(CapabilityType.PLATFORM_NAME, Platform.MAC);
        tpNode.put("technologyPreview", true);

        Map<String, Object> regularNode = new HashMap<>();
        regularNode.put(CapabilityType.BROWSER_NAME, BrowserType.SAFARI);
        regularNode.put(CapabilityType.PLATFORM_NAME, Platform.MAC);

        assertTrue(validator.apply(tpNode, requested));
        assertFalse(validator.apply(regularNode, requested));
    }


    @Test
    public void shouldMatchRegularSafariOnly() {

        Map<String, Object> requested = new SafariOptions().asMap();

        Map<String, Object> tpNode = new HashMap<>();
        tpNode.put(CapabilityType.BROWSER_NAME, "Safari Technology Preview");
        tpNode.put(CapabilityType.PLATFORM_NAME, Platform.MAC);

        Map<String, Object> regularNode = new HashMap<>();
        regularNode.put(CapabilityType.BROWSER_NAME, BrowserType.SAFARI);
        regularNode.put(CapabilityType.PLATFORM_NAME, Platform.MAC);

        assertFalse(validator.apply(tpNode, requested));
        assertTrue(validator.apply(regularNode, requested));
    }
}
