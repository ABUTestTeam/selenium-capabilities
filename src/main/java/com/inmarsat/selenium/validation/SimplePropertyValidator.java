package com.inmarsat.selenium.validation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.inmarsat.selenium.CapabilityHelper.anything;

/**
 *
 */
public class SimplePropertyValidator implements Validator {

    private static final String GRID_TOKEN = "_";

    private List<String> toConsider;

    public SimplePropertyValidator(String... toConsider) {
        this.toConsider = Arrays.asList(toConsider);
    }

    @Override
    public Boolean apply(Map<String, Object> providedCapabilities, Map<String, Object> requestedCapabilities) {
        return requestedCapabilities.entrySet().stream()
                .filter(entry -> ! entry.getKey().startsWith(GRID_TOKEN))
                .filter(entry -> toConsider.contains(entry.getKey()))
                .filter(entry -> ! anything(entry.getValue()))
                .allMatch(entry -> entry.getValue().equals(providedCapabilities.get(entry.getKey())));
    }
}
