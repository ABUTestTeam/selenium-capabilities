# Inmarsat's Custom Capability Matcher
[![Inmarsat Logo](https://spacenews.com/wp-content/uploads/2014/11/InmarsatLogo_Inmarsat4X3-879x485.jpg)](https://www.inmarsat.com)

## Prerequisites
- Java Development Kit 11
- Maven 3

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
   