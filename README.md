# MoneyBox Test For Android( MVI Architecture)





## Features 🎨

- **100% Kotlin-only project**.
-  Espresso,Mockwebserver REST Api Integration,Dagger2,Moshi,AndroidX,MVVM,Okhttp,RxBinding4,Rxjava3 Instrumentation & JUnit tests.
- 100% Gradle Kotlin DSL setup.
- Dependency versions managed via `buildSrc`.
- CI Setup with GitHub Actions.
- Kotlin Static Analysis via `ktlint` and `detekt`.
- Publishing Ready.
- Issues project (bug report + feature request)




### The Brief


Create a mini version of the Moneybox app that will allow existing users to login, check their account and add money to their moneybox.

The project currently contains a LoginActivity.kt with 3 EditTexts, a Button and a lovely animation of an owl that plays on the press of the button.  We want you to implement two additional screens.

## The screens

We want to give some useful functionality to our users. To allow them to log into the app, view and edit their account using our sandbox API (See API usage).

### Screen 1 - User accounts screen
This screen should be shown after the user has successfully logged in and should show have the following functionality:
- Display "Hello {name} **only** if they provided it on the previous screen"
- Show the **'TotalPlanValue'** of a user.
- Show the accounts the user holds, e.g. ISA, GIA, LISA, Pension.
- Show all of those account's **'PlanValue'**.
- Show all of those account's **'Moneybox'** total.

### Screen 2 - Individual account screen
If a user selects one of those accounts, they should then be taken to this screen.  This screen should have the following functionality:
- Show the **'Name'** of the account.
- Show the account's **'PlanValue'**.
- Show the accounts **'Moneybox'** total.
- Allow a user to add to a fixed value (e.g. £10) to their moneybox total.

A prototype wireframe of all 3 screens is provided as a guideline. You are free to change any elements of the screen and provide additional information if you wish.

![](/images/wireframe.png)

## What we are looking for:
 - An android application written in either Java or Kotlin.
 - Demonstration of coding style and design patterns.
 - Knowledge of common android libraries and any others that you find useful.
 - Storage of data between screens.
 - Consistency of data between screens.
 - Error handling.
 - Any form of unit or integration testing you see fit.
 - The application must run on Android 5.0 and above.
 - The application must compile and run in Android Studio.

Please feel free to modify/refactor the LoginActivity and use any libraries/helper methods to make your life easier.

## How to Submit your solution:
 - Zip up your solution, excluding any build artifacts to reduce the size, and email it back to our recruitment team.
 - Provide a readme in markdown with a brief summary of your application.

## API Usage
This is a brief summary of the api endpoints in the moneybox sandbox environment. There are a lot of other additional properties from the json responses that are not relevant, but you must use these endpoints to retrieve the information needed for this application.

#### Base URL & Test User
The base URL for the moneybox sandbox environment is `https://api-test01.moneyboxapp.com/`.
You can log into test your app using the following user:

#### |  Username          | Password         |
#### | ------------- | ------------- |
#### | jaeren+androidtest@moneyboxapp.com  | P455word12  |

#### Headers

In order to make requests https must be used and the following headers must be included in each request.

####|  Key | Value |
####| ------------- | ------------- |
####| AppId  | 3a97b932a9d449c981b595  |
####| Content-Type  | application/json  |
####| appVersion | 7.15.0 |
####| apiVersion | 3.0.0 |

#### Authentication
To login with this user and retrieve a bearer token you need to call `POST /users/login`.
```
POST /users/login
{
  "Email": "jaeren+androidtest@moneyboxapp.com",
  "Password": "P455word12",
  "Idfa": "ANYTHING"
}
```
Sample json response
```
"Session": {
        "BearerToken": "TsMWRkbrcu3NGrpf84gi2+pg0iOMVymyKklmkY0oI84=",
        "ExternalSessionId": "4ff0eab7-7d3f-40c5-b87b-68d4a4961983", -- not used
        "SessionExternalId": "4ff0eab7-7d3f-40c5-b87b-68d4a4961983", -- not used
        "ExpiryInSeconds": 0 -- not used
    }
```
After obtaining a bearer token an Authorization header must be provided for all other endpoints along with the headers listed above (Note: The BearerToken has a sliding expiration of 5 mins).

####|  Key          | Value         |
####| ------------- | ------------- |
####| Authorization  | Bearer TsMWRkbrcu3NGrpf84gi2+pg0iOMVymyKklmkY0oI84=  |

#### Investor Products
Provides product and account information for a user that will be needed for the two additional screens.
```
GET /investorproducts
```
### One off payments
Adds a one off amount to the users moneybox.
```
POST /oneoffpayments
{
  "Amount": 20,
  "InvestorProductId": 3230 ------> The InvestorProductId from /investorproducts endpoint
}
```
### OutStanding Task

- Increase Unit tests around current use of Mockwebserver
- More Unit tests
- Better Search view options
- Better arrangement of information current recyclerview
- Finishing adding PullToRefresh
- More robust regex testing
- Make a better UI


## Gradle Setup 🐘

This project is using [**Gradle Kotlin DSL**](https://docs.gradle.org/current/userguide/kotlin_dsl.html) as well as the [Plugin DSL](https://docs.gradle.org/current/userguide/plugins.html#sec:plugins_block) to setup the build.

Dependencies are centralized inside the [Dependencies.kt](buildSrc/src/main/java/Dependencies.kt) file in the `buildSrc` folder. This provides convenient auto-completion when writing your gradle files.

## Static Analysis 🔍

This project is using [**ktlint**](https://github.com/pinterest/ktlint) with the [ktlint-gradle](https://github.com/jlleitschuh/ktlint-gradle) plugin to format your code. To reformat all the source code as well as the buildscript you can run the `ktlintFormat` gradle task.

This project is also using [**detekt**](https://github.com/detekt/detekt) to analyze the source code, with the configuration that is stored in the [detekt.yml](config/detekt/detekt.yml) file (the file has been generated with the `detektGenerateConfig` task).

## CI ⚙️


There are currently the following workflows available:
- [Validate Gradle Wrapper](.github/workflows/gradle-wrapper-validation.yml) - Will check that the gradle wrapper has a valid checksum
- [Android Pull Request & Master CI](.github/workflows/workflow.yaml) - Will run the `build`, `check` and `assembleDebug` tasks.

## How To Build
Pull the code on this branch, import into Android Studio, from there you can run it like a standard
android project project or run ./gradlew assembleDebug. Further notes can be found here https://developer.android.com/studio/build/building-cmdline#DebugMode


## Testing
To test the **release** build variant locally:
1. Create key store file outside of the project directory. Please do not commit your keystore file to git.

2. Create or update `~/.gradle/gradle.properties` with:
```aidl
RELEASE_STORE_FILE=<path to key store file>
RELEASE_STORE_PASSWORD=<password>
RELEASE_KEY_ALIAS=<alias>
RELEASE_KEY_PASSWORD=<password>
```
3. Uncomment `signingConfigs` and `release.signingConfig` in app's `build.gradle`


## Publishing 🚀

The project is setup to be **ready to publish** a library/artifact on a Maven Repository. If you're using JitPack, you don't need any further configuration and you can just configure the repo on JitPack. If you're using another repository (MavenCentral/JCenter/etc.), you need to specify the publishing coordinates.
