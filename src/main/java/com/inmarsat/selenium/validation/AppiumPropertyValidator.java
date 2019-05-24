package com.inmarsat.selenium.validation;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.CapabilityType;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static com.inmarsat.selenium.CapabilityHelper.anything;
import static com.inmarsat.selenium.validation.PlatformValidator.extractPlatform;

public class AppiumPropertyValidator implements Validator{

    private final String[] propertyAliases;

    public AppiumPropertyValidator(String... propertyAliases){
        this.propertyAliases = propertyAliases;
    }


    @Override
    public Boolean apply(Map<String, Object> providedCapabilities, Map<String, Object> desiredCapabilities) {

        // Filter for appium only nodes (Android and IOS)
        if(providedCapabilities.get(CapabilityType.PLATFORM_NAME) == null
            || (!extractPlatform(providedCapabilities.get(CapabilityType.PLATFORM_NAME)).is(Platform.ANDROID)
            && !extractPlatform(providedCapabilities.get(CapabilityType.PLATFORM_NAME)).is(Platform.IOS))) {

            return true;
        }

        Object requested = Stream.of(propertyAliases)
                .map(desiredCapabilities::get)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        if (anything(requested)) {
            return true;
        }

        Object provided = Stream.of(propertyAliases)
                .map(providedCapabilities::get)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        return Objects.equals(requested, provided);
    }
}
