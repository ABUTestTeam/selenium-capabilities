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
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertTrue;

/**
 * Platform will be deprecated
 *
 * Version is currently used by browser version
 *
 * (new AliasedPropertyValidator(CapabilityType.BROWSER_VERSION, CapabilityType.VERSION))
 *
 */
public class PlatformValidatorTest {

    private Map<String, Object> providedCapabilities;

    private Map<String, Object> requestedCapabilities;


    @BeforeMethod
    public void setUp() {

        providedCapabilities = new HashMap<>();
        requestedCapabilities = new HashMap<>();
    }

    @Test
    public void testDifferentPlatform(){

        System.err.println("PLATFORM: " +  CapabilityType.PLATFORM);
        System.err.println("VERSION: " +  CapabilityType.VERSION);
        System.err.println("PLATFORM NAME: " + CapabilityType.PLATFORM_NAME);
        System.err.println("BROWSER NAME: " + CapabilityType.BROWSER_NAME);
        System.err.println("BROWSER VERSION: " + CapabilityType.BROWSER_VERSION);
        System.err.println("Platforms: "
                + Platform.YOSEMITE + " "
                + Platform.WINDOWS + " "
                + Platform.MAC + " "
                + Platform.WIN8 + " "
                + Platform.WIN8_1 + " "
                + Platform.WIN10 + " "
                + Platform.LINUX + " "
                + Platform.MAVERICKS + " "
                + Platform.VISTA + " ");

        providedCapabilities.put(CapabilityType.PLATFORM, "Win7");
        requestedCapabilities.put(CapabilityType.PLATFORM, "windows" );

        PlatformValidator validator = new PlatformValidator();

        assertTrue(validator.apply(providedCapabilities, requestedCapabilities));
    }
}
