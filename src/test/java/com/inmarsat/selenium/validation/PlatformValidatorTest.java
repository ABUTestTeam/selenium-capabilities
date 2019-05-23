package com.inmarsat.selenium.validation;

import com.google.common.collect.ImmutableMap;
import com.inmarsat.selenium.CustomCapabilitiesMatcher;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

public class PlatformValidatorTest {

    private CustomCapabilitiesMatcher matcher = new CustomCapabilitiesMatcher();

    // TODO remove test when CapabilityType.PLATFORM is removed from code base
    @Test
    public void smokeTestWithDeprecatedPlatformCapability() {

        Map<String, Object> firefox = ImmutableMap.of(
                CapabilityType.BROWSER_NAME, "B",
                CapabilityType.PLATFORM, "XP");

        Map<String, Object> tl = ImmutableMap.of(
                CapabilityType.APPLICATION_NAME, "A",
                CapabilityType.VERSION, "");

        Map<String, Object> firefox2 = ImmutableMap.of(
                CapabilityType.BROWSER_NAME, "B",
                CapabilityType.PLATFORM, "win7",
                CapabilityType.VERSION, "3.6");

        Map<String, Object> tl2 = ImmutableMap.of(
                CapabilityType.APPLICATION_NAME, "A",
                CapabilityType.VERSION, "8.5.100.7");


        assertTrue(matcher.matches(tl, tl));
        assertFalse(matcher.matches(tl, tl2));
        assertTrue(matcher.matches(tl2, tl));
        assertTrue(matcher.matches(tl2, tl2));

        assertTrue(matcher.matches(firefox, firefox));
        assertFalse(matcher.matches(firefox, firefox2));
        assertFalse(matcher.matches(firefox2, firefox));
        assertTrue(matcher.matches(firefox2, firefox2));

        assertFalse(matcher.matches(tl, null));
        assertFalse(matcher.matches(null, null));
        assertFalse(matcher.matches(tl, firefox));
        assertFalse(matcher.matches(firefox, tl2));
    }
}
