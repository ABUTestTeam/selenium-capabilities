package com.inmarsat.selenium.validation;

import com.inmarsat.selenium.CapabilityHelper;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.CapabilityType;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Compares platforms
 */
public class PlatformValidator implements Validator {

    @Override
    public Boolean apply(Map<String, Object> providedCapabilities, Map<String, Object> requestedCapabilities) {

        Object requested = Optional.ofNullable(requestedCapabilities.get(CapabilityType.PLATFORM))
                .orElse(requestedCapabilities.get(CapabilityType.PLATFORM_NAME));

        if (CapabilityHelper.anything(requested)) {
            return true;
        }

        Object provided = Optional.ofNullable(providedCapabilities.get(CapabilityType.PLATFORM))
                .orElse(providedCapabilities.get(CapabilityType.PLATFORM_NAME));

        Platform requestedPlatform = extractPlatform(requested);

        if (requestedPlatform != null) {
            Platform providedPlatform = extractPlatform(provided);
            return providedPlatform != null && providedPlatform.is(requestedPlatform);
        }

        return provided != null && Objects.equals(requested.toString(), provided.toString());
    }

    private Platform extractPlatform(Object o) {

        if (o == null) {
            return null;
        }

        if (o instanceof Platform) {
            return (Platform) o;
        }

        try {
            return Platform.fromString(o.toString());
        } catch (WebDriverException ex) {
            return null;
        }
    }
}
