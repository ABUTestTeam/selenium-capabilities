package com.inmarsat.selenium.validation;

import java.util.Map;

import static com.inmarsat.selenium.CapabilityHelper.anything;

/**
 * <p>Validates version properties in a flexible manner, it will match based on the first two digits
 * of a version and remove any additional text in the comparison.</p>
 *
 * <p>Example: providedCapability("version") = "version 1.61.3.4rc7", desiredCapability("version") = "1.61"
 * will return true.</p>
 *
 * <p>This enables the detachment of version and browserVersion so version can be used as platform version.</p>
 */
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
     * <p>This will parse the version, allowing flexibility in version naming. Still matching the key version
     * number.</p>
     *
     * <p>The function outputs a two number version number well formatted for comparison. Examples:</p>
     * <ul>
     *     <li>7 will return 7.0</li>
     *     <li>7.1 will return 7.1</li>
     *     <li>7.1.738.38291 will return 7.1</li>
     *     <li>v6.1 will return 6.1</li>
     *     <li>verison 9.1.23.432.rc1 will return 9.1</li>
     *     <li>1.1rc6 will return 1.16</li>
     * </ul>
     *
     * @param version the version string to parse
     *
     * @return a well formatted version string of format X.Y
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

