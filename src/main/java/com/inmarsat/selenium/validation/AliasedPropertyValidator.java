package com.inmarsat.selenium.validation;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static com.inmarsat.selenium.CapabilityHelper.anything;

/**
 * Generic validator
 */
public class AliasedPropertyValidator implements Validator {

    private String[] propertyAliases;

    public AliasedPropertyValidator(String... propertyAliases){
        this.propertyAliases = propertyAliases;
    }

    @Override
    public Boolean apply(Map<String, Object> providedCapabilities, Map<String, Object> requestedCapabilities) {

        Object requested = Stream.of(propertyAliases)
                .map(requestedCapabilities::get)
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
