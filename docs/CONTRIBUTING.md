# Contributing to the app

You can add new functionality to the app by following the instructions below.

## Table of contents

1. [Set up development environment](#set-up-development-environment)
1. [AndroidID's](#androidids)
1. [Building the app in the Android Studio](#building-the-app-in-the-android-studio)
1. [Building the app from the Terminal or Console](#building-the-app-from-the-terminal-or-console)
1. [Running Espresso tests on a local machine in the Android Studio](#running-espresso-tests-on-a-local-machine-in-the-android-studio)
1. [Running Espresso tests on a local machine in the CLI](#running-espresso-tests-on-a-local-machine-in-the-cli)
1. [Running Espresso tests in the Mobitru](#running-espresso-tests-in-the-mobitru)
1. [Libraries used](#libraries-used)
1. [Versioning the app](#versioning-the-app)

## Set up development environment

- install JDK11: https://adoptopenjdk.net/.
- install Android Studio: https://developer.android.com/studio/#downloads.
- setup Android 13 SDK: https://developer.android.com/about/versions/13/setup-sdk.
- add Google Pixel 5 or 6 virtual device: https://developer.android.com/studio/run/managing-avds.

## AndroidID's
Almost all elements inside this application have their own `android:id`, 
which is used for identifying elements in Tests and Accessibility Testing - https://developer.android.com/guide/topics/resources/layout-resource#idvalue

So, please always add a unique id to every new component in the code that can be used to interact with, or that displays text
For example, with a textview, the following code needs to be added

        <TextView
        android:id="@+id/price_text"
        ..../>

## Building the app in the Android Studio
> Make sure you have the latest version of the Gradle Android Plugin https://developer.android.com/build/releases/gradle-plugin

- open run configurations dialog: https://developer.android.com/studio/run/rundebugconfig.
- start to create a new configuration and select the "**Gradle**" template.
- specify command "**clean assembleDebug**" as Run value.
- make sure that "demo-android-app" project is selected.
- save changes.
- select the new Configuration in the Run Configurations dropdown.
- start a Run.

> The App will be assembled automatically
> The app-debug.apk file will be available in app/build/outputs/apk/debug directory

## Building the app from the Terminal or Console
> Make sure that the Gradle is installed and available in CLI https://gradle.org/install/

- go to the project root directory in CLI.
- perform `./gradlew clean assembleDebug` or `gradlew.bar clean assembleDebug`.
- wait for all dependencies will be downloaded and assembling will be finished.

> The app-debug.apk file will be available in app/build/outputs/apk/debug directory

## Running Espresso tests on a local machine in the Android Studio
> Make sure you've added a virtual device or connected to a real one via ADB

- open run configurations dialog: https://developer.android.com/studio/run/rundebugconfig.
- start to create a new configuration and select the "**Android Instrumented Tests**" template.
- specify aname (or leave the default "All Tests") and select "**Mobitru.app.androidTest**" module.
- select "All in Module" option in Test block.
- save changes and close dialog.
- select the new Configuration in the Run Configurations dropdown.
- select a Device in "Available devices" dropdown.
- start a Run.

> The App will be assembled and installed on the Device automatically
> Tests execution will be started after that


## Running Espresso tests on a local machine in the CLI

> Make sure you've connected to a Device or Emulator via ADB

- go to the project root directory in CLI
- perform `./gradlew clean connectedAndroidTest` or `gradlew.bar clean connectedAndroidTest`
- wait for all dependencies will be downloaded and assembling will be finished

> The App will be assembled and installed on the Device automatically
> Tests execution will be started after that

## Running Espresso tests in the Mobitru

Here is complete instruction on how to run Espresso Tests in the Mobitru
- https://docs.mobitru.com/run-espresso-tests/


## Libraries used

Detailed list with versions [here](../dependencies.gradle)

* androidx.core:core-ktx
* androidx.appcompat:appcompat
* androidx.constraintlayout:constraintlayout
* androidx.navigation:navigation-fragment-ktx
* androidx.navigation:navigation-ui-ktx
* androidx.biometric:biometric-ktx
* androidx.webkit:webkit
* androidx.camera:camera-camera2
* androidx.camera:camera-lifecycle
* androidx.camera:camera-view
* com.google.android.material:material
* com.github.lisawray.groupie:groupie
* com.github.lisawray.groupie:groupie-viewbinding
* com.squareup.picasso:picasso
* io.insert-koin:koin-core
* io.insert-koin:koin-android
* io.insert-koin:koin-android-compat
* io.insert-koin:koin-androidx-workmanager
* io.insert-koin:koin-androidx-navigation
* com.jakewharton.timber:timber
* com.google.mlkit:barcode-scanning
* junit:junit
* io.insert-koin:koin-test
* io.insert-koin:koin-test-junit4
* androidx.test.ext:junit
* androidx.test.espresso:espresso-core
* com.squareup.moshi:moshi:$moshiVersion
* com.squareup.moshi:moshi-kotlin-codegen
* com.squareup.retrofit2:retrofit
* com.squareup.retrofit2:converter-moshi
* com.squareup.okhttp3:logging-interceptor
* com.squareup.okhttp3:mockwebserver
* org.skyscreamer:jsonassert

## Versioning the app
Versioning the app will be done by incrementing **versionCode** and minor part of **versionName** 
in app [build.gradle](../app/build.gradle)
