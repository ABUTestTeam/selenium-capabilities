package com.inmarsat.selenium.validation;

import java.util.Map;

import static com.inmarsat.selenium.CapabilityHelper.anything;

public class VersionValidator implements Validator {

    private final String property;

    public VersionValidator(String property){
        this.property = property;
    }

    @Override
    public Boolean apply(Map<String, Object> providedCapabilities, Map<String, Object> desiredCapabilities) {


        if(desiredCapabilities.get(property) == null
                || anything(desiredCapabilities.get(property))) {
            return true;
        }

      return providedCapabilities.get(property) != null
                && parseVersion(desiredCapabilities.get(property).toString())
                .equals(parseVersion(providedCapabilities.get(property).toString()));
    }

    /**
     *
     * Need to account for:
     * 7 -> 7.0
     * 7.1 -> 7.1
     * 7.1.738.38291 -> 7.
     * Need to remove v if exists.
     * @param version
     * @return
     */
    private String parseVersion(String version) {

        String[] subString = version
                .replaceAll("\\s+","") // Removes whitespace
                .replaceAll("\\p{L}+","") // Removes alpha characters a-zA-Z
                .split("\\."); // Removes alpha characters

        if(subString.length == 0){
            return "";
        }else if (subString.length == 1){
            return subString[0] + ".0";
        }else {
            return subString[0] + "." + subString[1];
        }
    }
}

