# Inmarsat's Custom Capability Matcher


## Usage 

As selenium standalone executable is built using Java 8, this project also needs to be compiled at the same
version, hence the `<release>` tag is set to 8. Will work with JDK 8+.

### Docker 

1) Build the project
    
    Build with Maven `mvn clean package`, will clean, compile test and package into jar within the target folder. This 
    enough to use to build the docker image with the custom capabilties matcher in.
    
    `-DbuildNumber=0` specifes incremental numbering of commit versioning, i.e. 0.7.1254, where 1254 is the commit
    based numbering.

2) Build the Dockerfile

    Copies the jar into the container for use by the entry point. It will launch with the CustomCapabilitiesMatcher, 
    to edit this you need to adapt the 'generate_config' file and specify your own class-path to the CapabilityMatcher 
    to use.
    
    To build the Dockerfile, navigate to the project base directory, where the Dockerfile is and execute: 
    
    `docker build -f Dockerfile -t inmarsat/selenium-capabilities:${version} -t inmarsat/selenium-capabilities:latest .`
    
    For deploying to docker registry please refer to docker documentation. 
    
    To see the newly created docker images, that can be used within docker-compose and docker-machine, `docker images`.
    You will see two tags, inmarsat/selenium-capabilities:x.x.x and inmarsat/selenium-capabilities:latest.
    
    This docker image will work identically to the 
    [selenium/hub](https://github.com/SeleniumHQ/docker-selenium/tree/master/Hub) docker image, using the 
    `CustomCapabilitiesMatcher` provided.
    
    You can adjust the version of the grid within the Dockerfile, specifying the exact version as the base 
    image. This has only been tested working for selenium/base:3.141.59-neon.
    
 ## Use of Grid properties Desired and Provided
 
 | Name           | Java                             | Usage                                                                |
 |:---------------|:---------------------------------|:---------------------------------------------------------------------|
 | platformName   | `CapabilityType.PLATFORM_NAME`   | Name of the platform (i.e. Windows, Linux, Mac, Os X etc.)           |
 | version        | `CapabilityType.VERSION`         | Version of the platform (i.e. 7, v7, v10.0.6.12, etc.                |
 | browserName    | `CapabilityType.BROWSER_NAME`    | Also `"browser"`, the name of the browser (i.e. chrome, firefox, etc.) |
 | browserVersion | `CapabilityType.BROWSER_VERSION` | Version of the browser.                                              |
 | udid           | `MobileCapabilityType.UDID`      | The unique device identifier for android devices.                    |
 
 *NOTE:* `version` in `DefaultCapabilityMatcher` in the selenium project refers to an alternative for `browserVersion`. 
 Platform typically was referred to as `WIN8`, `WIN8_1` for platform version selection. Here we have seperated the two,
 it can however be used in the traditional way, where `platformName` can contain the version. However, the version 
 is no longer tied to the `browserVersion` as before.
 
 Added Features:
 
 * Handles versions by making them well-formatted "version 2183.123.2" is eq to "2183.123".

 Breaking changes between `DefaultCapabilityMatcher` and `CustomCapabilityMatcher`:
 * `"version"` no longer refers to browserVersion and is instead used for platform version separation.
 * version and browserVersion are matched to \<Major\>.\<Minor\> version schema for capability matching 
    * 7 -> 7.0
    * 7.1 -> 7.1
    * 7.0.1 -> 7.0
 * Removed `CapabilityType.PLATFORM` from the validator and associated tests as it is deprecated.
 _______________________________________________________________________________________________
 
[![Inmarsat Logo](https://spacenews.com/wp-content/uploads/2014/11/InmarsatLogo_Inmarsat4X3-879x485.jpg)](https://www.inmarsat.com)
