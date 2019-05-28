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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.openqa.selenium.remote.CapabilityType.BROWSER_NAME;
import static org.openqa.selenium.remote.CapabilityType.BROWSER_VERSION;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class AliasedPropertyValidatorTest extends AbstractValidatorTest {

    @BeforeMethod
    public void setUp() {

        setUpValidatorTest();
        providedCapabilities.put(BROWSER_NAME, "chrome");
        providedCapabilities.put(BROWSER_VERSION, "73.0");
    }

    @Test
    public void testBrowserNameMatches(){

        requestedCapabilities.put(BROWSER_NAME, "chrome");

        AliasedPropertyValidator validator = new AliasedPropertyValidator(BROWSER_NAME);

        assertTrue(validator.apply(providedCapabilities, requestedCapabilities));
    }

    @Test
    public void testBrowserNameDoesNotMatch(){

        requestedCapabilities.put(BROWSER_NAME, "firefox");

        AliasedPropertyValidator validator = new AliasedPropertyValidator(BROWSER_NAME);

        assertFalse(validator.apply(providedCapabilities, requestedCapabilities));
    }

    @Test
    public void testBrowserVersionMatch(){

        requestedCapabilities.put(BROWSER_VERSION, "73.0");

        AliasedPropertyValidator validator = new AliasedPropertyValidator(BROWSER_VERSION);

        assertTrue(validator.apply(providedCapabilities, requestedCapabilities));
    }

    @Test
    public void testAnyBrowserName(){

        // can be "any", "", or "*"
        requestedCapabilities.put(BROWSER_NAME, "*");

        AliasedPropertyValidator validator = new AliasedPropertyValidator(BROWSER_NAME);

        assertTrue(validator.apply(providedCapabilities, requestedCapabilities));
    }

    @Test
    public void testAnyConfig(){

        AliasedPropertyValidator validator = new AliasedPropertyValidator(BROWSER_NAME, BROWSER_VERSION);

        assertTrue(validator.apply(providedCapabilities, requestedCapabilities));
    }

    @Test
    public void testMultipleDoesNotMatch(){
        requestedCapabilities.put(BROWSER_NAME, "firefox");
        requestedCapabilities.put(BROWSER_VERSION, "00");

        AliasedPropertyValidator validator = new AliasedPropertyValidator(BROWSER_NAME, BROWSER_VERSION);

        assertFalse(validator.apply(providedCapabilities, requestedCapabilities));
    }

}
