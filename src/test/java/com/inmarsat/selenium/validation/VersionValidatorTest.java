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

import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class VersionValidatorTest extends AbstractValidatorTest{

    @BeforeMethod
    public void setUp() {

        setUpValidatorTest();
        validator = new VersionValidator(CapabilityType.VERSION);
    }

    @Test
    public void testNullProvided(){
        providedCapabilities.put(CapabilityType.VERSION, null );
        requestedCapabilities.put(CapabilityType.VERSION, "8.1.1267138" );

        assertFalse(validator.apply(providedCapabilities, requestedCapabilities));
    }

    @Test
    public void testNullDesired(){

        providedCapabilities.put(CapabilityType.VERSION, "8.1.126738" );
        requestedCapabilities.put(CapabilityType.VERSION, null );

        assertTrue(validator.apply(providedCapabilities, requestedCapabilities));
    }

    @Test
    public void testVersionTwoAndThreeDecimal(){

        providedCapabilities.put(CapabilityType.VERSION, "8.1");
        requestedCapabilities.put(CapabilityType.VERSION, "8.1.126738" );

        assertTrue(validator.apply(providedCapabilities, requestedCapabilities));
    }


    @Test
    public void testWrongMinorVersion(){

        providedCapabilities.put(CapabilityType.VERSION, "8.2");
        requestedCapabilities.put(CapabilityType.VERSION, "8.1" );

        assertFalse(validator.apply(providedCapabilities, requestedCapabilities));
    }

    @Test
    public void testWrongMajorVersion(){

        providedCapabilities.put(CapabilityType.VERSION, "7.1");
        requestedCapabilities.put(CapabilityType.VERSION, "8.1" );

        assertFalse(validator.apply(providedCapabilities, requestedCapabilities));
    }

    @Test
    public void testSingleVersionNumber(){
        providedCapabilities.put(CapabilityType.VERSION, "7");
        requestedCapabilities.put(CapabilityType.VERSION, "7.0" );

        assertTrue(validator.apply(providedCapabilities, requestedCapabilities));
    }

    @Test
    public void testComplexVersionsMatch(){
        providedCapabilities.put(CapabilityType.VERSION, "7.126743.3728.1283.1274.123");
        requestedCapabilities.put(CapabilityType.VERSION, "7.126743.3543.12.46.1.34" );

        assertTrue(validator.apply(providedCapabilities, requestedCapabilities));
    }

    @Test
    public void testRemoveVersionV(){
        providedCapabilities.put(CapabilityType.VERSION, "7.1");
        requestedCapabilities.put(CapabilityType.VERSION, "V7.1" );

        assertTrue(validator.apply(providedCapabilities, requestedCapabilities));
    }

    @Test
    public void testRemoveVersionVWithSpace(){
        providedCapabilities.put(CapabilityType.VERSION, "7.1");
        requestedCapabilities.put(CapabilityType.VERSION, "v 7.1" );

        assertTrue(validator.apply(providedCapabilities, requestedCapabilities));
    }

    @Test
    public void testRemoveVersionVWithSpaceAndMultipleCharacters(){
        providedCapabilities.put(CapabilityType.VERSION, "7.1");
        requestedCapabilities.put(CapabilityType.VERSION, "version 7.1" );

        assertTrue(validator.apply(providedCapabilities, requestedCapabilities));
    }
}
