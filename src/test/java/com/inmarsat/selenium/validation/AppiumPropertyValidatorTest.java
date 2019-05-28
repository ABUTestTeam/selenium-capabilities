/*
 * Copyright 2019 Inmarsat plc.
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

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertFalse;

public class AppiumPropertyValidatorTest extends AbstractValidatorTest {

    @BeforeMethod
    public void setUp(){
        setUpValidatorTest();

        validator = new AppiumPropertyValidator(MobileCapabilityType.UDID,
                MobileCapabilityType.DEVICE_NAME);

        providedCapabilities.put(CapabilityType.PLATFORM_NAME, "Android");
        requestedCapabilities.put(CapabilityType.PLATFORM_NAME, "android");
    }

    @Test
    public void testDeviceName(){
        providedCapabilities.put(MobileCapabilityType.DEVICE_NAME, "6FgC");
        requestedCapabilities.put(MobileCapabilityType.DEVICE_NAME, "6FgC");

        assertTrue(validator.apply(providedCapabilities, requestedCapabilities));
        assertTrue(validator.apply(requestedCapabilities, providedCapabilities));
    }

    @Test
    public void testDeviceNameNoMatch(){
        providedCapabilities.put(MobileCapabilityType.DEVICE_NAME, "6FgC");
        requestedCapabilities.put(MobileCapabilityType.DEVICE_NAME, "6FgC3");

        assertFalse(validator.apply(providedCapabilities, requestedCapabilities));
        assertFalse(validator.apply(requestedCapabilities, providedCapabilities));
    }

    @Test
    public void testMixAndMatch(){
        providedCapabilities.put(MobileCapabilityType.UDID, "ABCD");
        requestedCapabilities.put(MobileCapabilityType.DEVICE_NAME, "ABCD");

        assertTrue(validator.apply(providedCapabilities, requestedCapabilities));
        assertTrue(validator.apply(requestedCapabilities, providedCapabilities));
    }

    @Test
    public void testPriorityUDID(){
        providedCapabilities.put(MobileCapabilityType.UDID, "ABCD");
        requestedCapabilities.put(MobileCapabilityType.DEVICE_NAME, "ABCDEF");
        requestedCapabilities.put(MobileCapabilityType.UDID, "ABCD");

        assertTrue(validator.apply(providedCapabilities, requestedCapabilities));
        assertTrue(validator.apply(requestedCapabilities, providedCapabilities));
    }
}

