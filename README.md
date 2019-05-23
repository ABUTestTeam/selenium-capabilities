# Custom Capability Matcher
Example that shows how to implement a Selenium Grid capability matcher, implementation of a tutorial that can be
found [here](https://rationaleemotions.wordpress.com/2014/01/19/working-with-a-custom-capability-matcher-in-the-grid/).

## How to generate the jar
_It will be placed in the target folder_
```
    $ mvn -DskipTests=true package
```

## Steps to start the grid
1. Download Selenium Server

  ```
    $ wget http://selenium-release.storage.googleapis.com/2.53/selenium-server-standalone-2.53.0.jar
  ```
1. Start the hub with a the specific configuration

  ```
    $ java -cp selenium-server-standalone-2.53.0.jar:target/custom-capability-matcher-1.0-SNAPSHOT.jar org.openqa.grid.selenium.GridLauncher -role hub -hubConfig src/main/resources/hubConfig.json
  ```