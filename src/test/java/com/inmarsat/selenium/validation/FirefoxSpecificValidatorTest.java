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

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class FirefoxSpecificValidatorTest extends AbstractValidatorTest {

    @BeforeMethod
    public void setUp(){
        setUpValidatorTest();
        validator = new FirefoxSpecificValidator();
    }

    @Test
    public void shouldMatchMarionetteFirefoxDriverOnly() {

        Map<String, Object> requested = new FirefoxOptions().asMap();

        Map<String, Object> legacyNode = new HashMap<>();

        legacyNode.put(CapabilityType.BROWSER_NAME, BrowserType.FIREFOX);
        legacyNode.put(FirefoxDriver.MARIONETTE, false);

        Map<String, Object> mNode = new HashMap<>();
        mNode.put(CapabilityType.BROWSER_NAME, BrowserType.FIREFOX);

        assertFalse(validator.apply(legacyNode, requested));
        assertTrue(validator.apply(mNode, requested));
    }
}
