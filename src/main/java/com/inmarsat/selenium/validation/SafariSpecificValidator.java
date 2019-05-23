package com.inmarsat.selenium.validation;

import org.openqa.selenium.ImmutableCapabilities;
import org.openqa.selenium.safari.SafariOptions;

import java.util.Map;

import static org.openqa.selenium.remote.CapabilityType.BROWSER_NAME;

public class SafariSpecificValidator implements Validator {

    @Override
    public Boolean apply(Map<String, Object> providedCapabilities, Map<String, Object> requestedCapabilities) {
        if (!"safari".equals(requestedCapabilities.get(BROWSER_NAME)) &&
                !"Safari Technology Preview".equals(requestedCapabilities.get(BROWSER_NAME))) {
            return true;
        }

        SafariOptions providedOptions = new SafariOptions(new ImmutableCapabilities(providedCapabilities));
        SafariOptions requestedOptions = new SafariOptions(new ImmutableCapabilities(requestedCapabilities));

        return requestedOptions.getAutomaticInspection() == providedOptions.getAutomaticInspection() &&
                requestedOptions.getAutomaticProfiling() == providedOptions.getAutomaticProfiling() &&
                requestedOptions.getUseTechnologyPreview() == providedOptions.getUseTechnologyPreview();
    }
}
