package com.inmarsat.selenium.validation;

import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class VersionValidatorTest {

    private Map<String, Object> providedCapabilities;

    private Map<String, Object> requestedCapabilities;


    @BeforeMethod
    public void setUp() {

        providedCapabilities = new HashMap<>();
        requestedCapabilities = new HashMap<>();
    }

    @Test
    public void testVersionTwoAndThreeDecimal(){

        providedCapabilities.put(CapabilityType.VERSION, "8.1");
        requestedCapabilities.put(CapabilityType.VERSION, "8.1.126738" );

        VersionValidator validator = new VersionValidator(CapabilityType.VERSION);

        assertTrue(validator.apply(providedCapabilities, requestedCapabilities));
    }


    @Test
    public void testWrongMinorVersion(){

        providedCapabilities.put(CapabilityType.VERSION, "8.2");
        requestedCapabilities.put(CapabilityType.VERSION, "8.1" );

        VersionValidator validator = new VersionValidator(CapabilityType.VERSION);

        assertFalse(validator.apply(providedCapabilities, requestedCapabilities));
    }

    @Test
    public void testWrongMajorVersion(){

        providedCapabilities.put(CapabilityType.VERSION, "7.1");
        requestedCapabilities.put(CapabilityType.VERSION, "8.1" );

        VersionValidator validator = new VersionValidator(CapabilityType.VERSION);

        assertFalse(validator.apply(providedCapabilities, requestedCapabilities));
    }

    @Test
    public void testSingleVersionNumber(){
        providedCapabilities.put(CapabilityType.VERSION, "7");
        requestedCapabilities.put(CapabilityType.VERSION, "7.0" );

        VersionValidator validator = new VersionValidator(CapabilityType.VERSION);

        assertTrue(validator.apply(providedCapabilities, requestedCapabilities));
    }

    @Test
    public void testComplexVersionsMatch(){
        providedCapabilities.put(CapabilityType.VERSION, "7.126743.3728.1283.1274.123");
        requestedCapabilities.put(CapabilityType.VERSION, "7.126743.3543.12.46.1.34" );

        VersionValidator validator = new VersionValidator(CapabilityType.VERSION);

        assertTrue(validator.apply(providedCapabilities, requestedCapabilities));
    }

    @Test
    public void testRemoveVersionV(){
        providedCapabilities.put(CapabilityType.VERSION, "7.1");
        requestedCapabilities.put(CapabilityType.VERSION, "V7.1" );

        VersionValidator validator = new VersionValidator(CapabilityType.VERSION);

        assertTrue(validator.apply(providedCapabilities, requestedCapabilities));
    }

    @Test
    public void testRemoveVersionVWithSpace(){
        providedCapabilities.put(CapabilityType.VERSION, "7.1");
        requestedCapabilities.put(CapabilityType.VERSION, "v 7.1" );

        VersionValidator validator = new VersionValidator(CapabilityType.VERSION);

        assertTrue(validator.apply(providedCapabilities, requestedCapabilities));
    }

    @Test
    public void testRemoveVersionVWithSpaceAndMultipleCharacters(){
        providedCapabilities.put(CapabilityType.VERSION, "7.1");
        requestedCapabilities.put(CapabilityType.VERSION, "version 7.1" );

        VersionValidator validator = new VersionValidator(CapabilityType.VERSION);

        assertTrue(validator.apply(providedCapabilities, requestedCapabilities));
    }
}
