# Inmarsat's Custom Capability Matcher
[![Inmarsat Logo](https://spacenews.com/wp-content/uploads/2014/11/InmarsatLogo_Inmarsat4X3-879x485.jpg)](https://www.inmarsat.com)

## Prerequisites
- Java Development Kit 11

## Building the project
```
$ mvn clean install -DbuildNumber=0
```
_This will produce a jar in `${user.home}/.m2/repository/com/inmarsat/selenium-capabilities/${build.version}\"`._

## To use the CustomCapabilitiesMatcher

1. Download Selenium Server

  ```
    $ wget http://selenium-release.storage.googleapis.com/2.53/selenium-server-standalone-2.53.0.jar
  ```
2. Start the hub with a the specific configuration

  ```
    $ java -cp /path/to/selenium-server-standalone-3.159.1.jar:/path/to/selenium-capabilities-${build.version}.jar org.openqa.grid.selenium.GridLauncher -role hub -hubConfig hubConfig.json
  ```
 ## Use of Grid properties Desired and Provided
 
 | Name           | Java                           | Usage                                                                |
 |:--------------:|:------------------------------:|:---------------------------------------------------------------------|
 | platformName   | CapabilityType.PLATFORM_NAME   | Name of the platform (i.e. Windows, Linux, Mac, Os X etc.)           |
 | version        | CapabilityType.VERSION         | Version of the platform (i.e. 7, v7, v10.0.6.12, etc.                |
 | browserName    | CapabilityType.BROWSER_NAME    | Also "browser", the name of the browser (i.e. chrome, firefox, etc.) |
 | browserVersion | CapabilityType.BROWSER_VERSION | Version of the browser.                                              |
 
 *NOTE:* version in DefaultCapabilityMatcher in the selenium project refers to an alternative for browserVersion. 
 Platform typically was referred to as WIN8, WIN8_1 for platform version selection. Here we have seperated the two,
 it can however be used in the traditional way, where platformName can contain the version. However, the version 
 is no longer tied to the browserVersion as before.
 
 Added Features:
 * Handles versions by making them well-formatted "version 2183.123.2" is eq to "2183.123".

 Breaking changes between DefaultCapabilityMatcher and CustomCapabilityMatcher:
 * "version" no longer refers to browserVersion and is instead used for platform version separation.
 * version and browserVersion are matched to \<Major\>.\<Minor\> version schema for capability matching 
    * 7 -> 7.0
    * 7.1 -> 7.1
    * 7.0.1 -> 7.0
 * Removed CapabilityType.PLATFORM from the validator and associated tests as it is deprecated.