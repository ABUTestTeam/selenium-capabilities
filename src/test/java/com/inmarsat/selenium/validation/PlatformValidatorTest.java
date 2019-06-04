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

import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Platform will be deprecated
 *
 * Version is currently used by browser version
 *
 * (new AliasedPropertyValidator(CapabilityType.BROWSER_VERSION, CapabilityType.VERSION))
 *
 */
public class PlatformValidatorTest extends AbstractValidatorTest {

    @BeforeMethod
    public void setUp() {

        setUpValidatorTest();
        validator = new PlatformValidator();
    }

    @Test
    public void testDifferentPlatform(){

        providedCapabilities.put(CapabilityType.PLATFORM_NAME, "Win7");
        requestedCapabilities.put(CapabilityType.PLATFORM_NAME, "windows" );

        assertTrue(validator.apply(providedCapabilities, requestedCapabilities));
    }
}
