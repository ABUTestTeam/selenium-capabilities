package com.inmarsat.selenium.validation;

import java.util.Map;

import static org.openqa.selenium.remote.CapabilityType.BROWSER_NAME;

public class FirefoxSpecificValidator implements Validator {

    @Override
    public Boolean apply(Map<String, Object> providedCapabilities, Map<String, Object> requestedCapabilities) {
        if (! "firefox".equals(requestedCapabilities.get(BROWSER_NAME))) {
            return true;
        }

        if (requestedCapabilities.get("marionette") != null &&
                !Boolean.valueOf(requestedCapabilities.get("marionette").toString())) {
            return providedCapabilities.get("marionette") != null &&
                    !Boolean.valueOf(providedCapabilities.get("marionette").toString());
        } else {
            return providedCapabilities.get("marionette") == null ||
                    Boolean.valueOf(providedCapabilities.get("marionette").toString());
        }
    }
}
