package com.inmarsat.selenium.validation;

import java.util.HashMap;
import java.util.Map;

class AbstractValidatorTest {

    Map<String, Object> providedCapabilities;
    Map<String, Object> requestedCapabilities;

    Validator validator;

    void setUpValidatorTest(){
        providedCapabilities = new HashMap<>();
        requestedCapabilities = new HashMap<>();
    }

}
