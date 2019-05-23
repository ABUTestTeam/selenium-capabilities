package com.inmarsat.selenium;

import com.google.common.collect.ImmutableSet;

public class CapabilityHelper {

    private CapabilityHelper(){
        // EMPTY
    }

    public static boolean anything(Object requested) {
        return requested == null ||
                ImmutableSet.of("any", "", "*").contains(requested.toString().toLowerCase());
    }

}
