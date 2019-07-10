#!/usr/bin/env groovy

properties([
        // Set to trigger polling of changes on the branches themselves - will trigger build
        pipelineTriggers([
                pollSCM('H/5 * * * *')
        ])
])

/**
 * Start build stages
 */
stage("Unit Tests") {
    try {

        timeout(time: 90, unit: 'SECONDS') {
            node('docker-build-slave') {

                def fullVersionNumber = initSCM()

                slackSend color: getMessageColour(), message: "Started Build for v${fullVersionNumber}\n" +
                        "Job Name: ${env.JOB_NAME}, you can view the build by opening the link (<${env.RUN_DISPLAY_URL}|link>).\n" +
                        "Commit Author: " + getGitAuthor() + "\n" +
                        "Commit Message: " + getLastCommitMessage()

                try {

                    runUnitTests(getVersionNumber())

                } catch (exception) {

                    emailLogs(fullVersionNumber)

                    throw exception
                }
                finally {

                    generateUnitTestReport("Selenium_Capabilities_Unit_Tests", getVersionNumber())

                    slackSend color: getMessageColour(), message: "Unit Tests (v${fullVersionNumber})\n" +
                            "Job Name: ${env.JOB_NAME}\n" +
                            "<${env.RUN_DISPLAY_URL}| Link to Build>\n" +
                            "Branch: ${env.BRANCH_NAME}\n" + getTestSummary()
                }
            }
        }
    } catch (org.jenkinsci.plugins.workflow.steps.FlowInterruptedException e) {

        handleInterruptedException(e, "90", "Unit Test")
    }
}

stage("Code Review and Coverage Report Generation") {
    try {

        timeout(time: 180, unit: 'SECONDS') {

            node('docker-build-slave') {

                def fullVersionNumber = initSCM()

                try {
                    // Sets environment allowing Jenkins access to server
                    withSonarQubeEnv('SonarQube Server') {
                        runStaticAnalysisSonarQube(getVersionNumber())
                    }

                    slackSend color: getMessageColour(), message: "v${fullVersionNumber} code code review completed " +
                            "(<http://192.168.34.68:9000/dashboard?id=CAPABILITIES_MATCHER|link>)"

                } catch (exception) {

                    slackSend color: getMessageColour(), message: "v${fullVersionNumber} code code review failed " +
                            "<${env.RUN_DISPLAY_URL}| Link to Build>"

                    throw exception

                }
            }
        }
    } catch (org.jenkinsci.plugins.workflow.steps.FlowInterruptedException e) {

        handleInterruptedException(e, "180", "Code Review")
    }
}

stage("Quality Gate") {

    sleep(10)

    try {
        timeout(time: 180, unit: 'SECONDS') {

            def qualityGate = waitForQualityGate()

            if (qualityGate.status != "OK") {

                currentBuild.result = 'FAILED'

                slackSend color: getMessageColour(), message: "Build failed as one or more quality requirements" +
                        "have not been met, check <http://192.168.34.68:9000|SonarQube> for more details."

                error("Failed because the quality requirements have not been met")
            } else {
                slackSend color: getMessageColour(), message: "<${env.RUN_DISPLAY_URL}|Link to Build> - Quality Checks have passed, " +
                        "check <http://192.168.34.68:9000|SonarQube> for more details."
            }
        }
    } catch (org.jenkinsci.plugins.workflow.steps.FlowInterruptedException e) {

        handleInterruptedException(e, "180", "Quality Gate")
    }
}

stage("Create build artifact after successful tests") {
    try {

        timeout(time: 180, unit: 'SECONDS') {
            node('docker-build-slave') {

                def fullVersionNumber = initSCM()

                createJar(getVersionNumber())

                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true, onlyIfSuccessful: true

                slackSend color: getMessageColour(), message: "v${fullVersionNumber} Build Completed\n" +
                        "<${env.BUILD_URL}|Click here to view the results>\n" +
                        "Overall build Status : ${currentBuild.currentResult}"

                if (env.BRANCH_NAME.contains("master")) {

                    try {
                        echo "Deploying artifacts to Apache Archiva"

                        deployToArchiva("selenium-capabilities", "internal", fullVersionNumber)

                        slackSend color: getMessageColour(), message: "v${fullVersionNumber} of the Selenium Capabilities has been successfully deployed to Archiva " +
                                "(<http://192.168.34.66:8080/#artifact/com.inmarsat/selenium-capabilities/${fullVersionNumber}|link>)"

                    } catch (exception) {

                        slackSend color: getMessageColour(), message: "Deployment of automation v${fullVersionNumber} failed: ${exception.getCause().get(0)}"

                        emailLogs(fullVersionNumber)

                        throw exception
                    }

                    // Adds credentials securely to git commands
                    withCredentials([usernamePassword(credentialsId: 'git_pgaucherand', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {

                        // Also adds commit messages since last tag
                        tagSCM("selenium-capabilities", fullVersionNumber, "${GIT_USERNAME}", "${GIT_PASSWORD}")
                    }
                }
            }
        }
    } catch (org.jenkinsci.plugins.workflow.steps.FlowInterruptedException e) {

        handleInterruptedException(e, "180", "Code Review")
    }
}