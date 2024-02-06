# Automation

## Table of contents
1. [Intro](#intro)
1. [Set up test development environment](#set-up-test-development-environment)
1. [Writing tests](#writing-tests)
1. [Running tests on a local machine with an emulator](#running-tests-on-a-local-machine-with-an-emulator)
1. [Running tests in the Mobitru](#running-tests-in-the-mobitru)
    1. [The setup](#the-setup)
    1. [Running tests](#running-tests)


## Intro
Tests are run with:

* [Espresso Core](https://developer.android.com/training/testing/espresso): This is Android UI tests framework for performing actions and making assertions 
* [Espresso Accessibility](https://developer.android.com/training/testing/espresso/accessibility-checking): This is part of the Espresso framework, which is adding accessibility checks to existing Espresso tests  
* [Junit 4](https://junit.org/junit4/): The testing framework that is used to organize and execute tests
* [Gradle](https://gradle.org/): The build tool for assemble the App and start tests execution


## Set up test development environment

- install JDK11: https://adoptopenjdk.net/
- install Android Studio: https://developer.android.com/studio/#downloads
- setup Android 13 SDK: https://developer.android.com/about/versions/13/setup-sdk
- add Google Pixel 5 or 6 virtual device: https://developer.android.com/studio/run/managing-avds

## Writing tests
You can find the current tests in [this](../app/src/androidTest/java/com/epam/mobitru)-folder to see how the tests are being written. Base information about the
actions and assertions that Espresso supports you can find in [this](https://developer.android.com/training/testing/espresso/basics) site.

### Working with elements
For finding elements, you can use Resource IDs. Every such unique name is usually related to a specific Element inside a View.
More information can be found on [this](https://developer.android.com/guide/topics/resources/layout-resource#idvalue) site.
If necessary, you can change or specify a Resource ID in a Layout XMS, which is located [here](../app/src/main/res/layout) 

### Working with list
For finding an Element inside a ListView you need to use specific [ViewMatcher](../app/src/androidTest/java/com/epam/mobitru/matchers/RViewMatcher.java), 
where it's possible to specify the ListView Resource ID and index of necessary Element. As result, you will receive ```Matcher<View>```, which can be used for performing actions and making assertions

### Accessibility testing
The Accessibility Checks were configured in [AppBaseTest](../app/src/androidTest/java/com/epam/mobitru/AppBaseTest.java) for verifying the app versatility 
There are 2 configurations in code, which can be used for accessibility test automation:
- pre-release set of checks for whole root view, but excluding several verifications for specific elements (**default**)
- base set of checks for view action only, which will be performed for any element

**!!** for second configuration you will see accessible verification fails, which will demonstrate how it looks in a tests result Report and an execution log  

## Running tests on a local machine with an emulator
> Make sure you've added a virtual device

- open run configurations dialog: https://developer.android.com/studio/run/rundebugconfig
- start to create a new configuration and select the "**Android Instrumented Tests**" template
- specify a name (or leave the default "All Tests") and select "**Mobitru.app.androidTest**" module
- select "All in Module" option in Test block
- save changes and close dialog
- select the new Configuration in the Run Configurations dropdown
- select the virtual Device in "Available devices" dropdown
- start a Run

> The App will be assembled and installed on the Device automatically
> Test(s) execution will be started after that

## Running tests in the Mobitru

### The setup
The setup includes building the App and apk file with Tests.
Also, it will be necessary to upload them to Mobitru internal storage.

For building the App:

- open run configurations dialog: https://developer.android.com/studio/run/rundebugconfig
- start to create a new configuration and select the "**Gradle**" template
- specify command "**clean assembleDebug**" as Run value
- make sure that "demo-android-app" project is selected
- save changes
- select the new Configuration in the Run Configurations dropdown
- start a Run
- wait for completion and take the apk file from `app/build/outputs/apk/debug`

For building the apk with Tests:

- open run configurations dialog again
- start to create a new configuration and select the "**Gradle**" template
- specify command "**clean assembleAndroidTest**" as Run value
- make sure that "demo-android-app" project is selected
- save changes
- select the new Configuration in the Run Configurations dropdown
- start a Run
- wait for completion and take the apk file from `app/build/outputs/apk/debug`

> As final preparation step you need to upload APKs using the following API - https://app.mobitru.com/wiki/api/#api-InstallApp-UploadFile

### Running tests
To run the Espresso tests in the Mobitru please take an Android Device (in a browser or via API) and save its UDID.
Then, you can trigger the Espresso tests executing by call [this](https://app.mobitru.com/wiki/api/#api-Espresso_[experimental]-CreateEspressoTestRun) API.

> For more details, see the complete guide- https://docs.mobitru.com/run-espresso-tests/
