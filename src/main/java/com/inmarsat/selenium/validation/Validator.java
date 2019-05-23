package com.inmarsat.selenium.validation;

import java.util.Map;
import java.util.function.BiFunction;

public interface Validator extends BiFunction<Map<String, Object>, Map<String, Object>, Boolean> {
    // void implementation
}
