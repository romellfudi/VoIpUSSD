<h1 align="center">
  VOIP USSD ANDROID
</h1>   

<p align="center"> 
    <a href="https://developer.android.com/index.html"><img src="https://img.shields.io/badge/platform-android-brightgreen.svg?&amp;logo=android" alt="Platform"></a>
<a href="https://android-arsenal.com/api?level=23"><img src="https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat&amp;logo=android" alt="API"></a>
<a href="https://github.com/romellfudi/VoIpUSSDSample/blob/master/LICENSE"><img src="https://img.shields.io/badge/license-Apache%202.0-blue.svg?&amp;logo=apache" alt="License"></a>
<a href="https://github.com/romellfudi/VoIpUSSD/actions"><img src="https://github.com/romellfudi/VoIpUSSD/workflows/Android%20CI/badge.svg" alt="Workflow"></a>
<a href="https://gitter.im/romellfudi/VoIpUSSD?utm_source=badge&amp;utm_medium=badge&amp;utm_campaign=pr-badge"><img src="https://img.shields.io/badge/gitter-join%20us-%23753a89.svg?&amp;style=flat&amp;logo=gitter&amp;labelColor=ee1a67" alt="Gitter"></a>
<a href="https://bintray.com/romllz489/maven/ussd-library"><img src="https://img.shields.io/bintray/v/romllz489/maven/ussd-library.svg?&amp;logo=jfrog-bintray" alt="Bintray"></a>
<a href="https://bintray.com/romllz489/maven/kotlin-ussd-library"><img src="https://img.shields.io/bintray/v/romllz489/maven/kotlin-ussd-library.svg?&amp;logo=jfrog-bintray" alt="Bintray Kotlin"></a>
<a href="https://android-arsenal.com/details/1/7151"><img src="https://img.shields.io/badge/Android%20Arsenal-Void%20USSD%20Library-green.svg?style=flat?&amp;logo=android-studio" alt="Android Arsenal"></a>
<a href="https://jitpack.io/#romellfudi/VoIpUSSD"><img src="https://jitpack.io/v/romellfudi/VoIpUSSD.svg" alt="Jitpack"></a> 
</p>

<p align="center">
<i>Loved the tool? Please consider <a href="https://paypal.me/romellfudi/15">donating</a>  💸 to help it improve!</i>
</p>

<p align="center">
<a href="https://www.paypal.me/romellfudi"><img src="https://img.shields.io/badge/support-PayPal-blue?logo=PayPal&style=flat-square&label=Donate" alt="sponsor voip android library"/>
</a>
  
<p align="center">
  <a href="#ussd-library">Usage</a>
  &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#static-methods">Extra Features</a>
  &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#cordova-plugin">Cordova Plugin</a>
  &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#contributors-">Contributors</a>
</p>


### by Romell Dominguez
[![](https://raw.githubusercontent.com/romellfudi/assets/master/favicon.ico)](https://www.romellfudi.com/)

## Target Development [High Quality](https://raw.githubusercontent.com/romellfudi/VoIpUSSD/Rev04/snapshot/device_recored.gif):
<p align="center"> <a href="https://raw.githubusercontent.com/romellfudi/VoIpUSSD/Rev04/snapshot/device_recored.gif"><img src="https://raw.githubusercontent.com/romellfudi/VoIpUSSD/Rev04/snapshot/device_recored.gif" alt="Jitpack"></a> </p>

Interactive with ussd windows, remember the USSD interfaces depends on the System Operative and the manufacturer of Android devices.

## Community Chat
[![Gitter](https://img.shields.io/badge/gitter-join%20us-%23753a89.svg?&style=for-the-badge&logo=gitter&labelColor=ee1a67)](https://gitter.im/romellfudi/VoIpUSSD?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

## Downloads Dashboard
[![Data Studio](https://img.shields.io/badge/DATA%20STUDIO-DOWNLOADS%20REPORT-%23000000.svg?&style=for-the-badge&logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyBpZD0iTGF5ZXJfMSIgZGF0YS1uYW1lPSJMYXllciAxIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAxMDcuNDcgMTAwIj48ZGVmcz48c3R5bGU%2BLmNscy0xe2ZpbGw6IzY2OWRmMzt9LmNscy0ye2ZpbGw6IzFhNzNlODt9PC9zdHlsZT48L2RlZnM%2BPHBhdGggY2xhc3M9ImNscy0xIiBkPSJNOTQuMDcsNzIuNzVINTVjLTkuNDYsMC0xNC43Myw2LjU5LTE0LjczLDEzLjYyQzQwLjIyLDkyLjc1LDQ0LjYyLDEwMCw1NSwxMDBoMzguOVY3Mi43NVoiLz48ZWxsaXBzZSBjbGFzcz0iY2xzLTIiIGN4PSI5NC4wNyIgY3k9Ijg2LjE1IiByeD0iMTMuNDEiIHJ5PSIxMy42MyIvPjxwYXRoIGNsYXNzPSJjbHMtMSIgZD0iTTUzLjYzLDM2LjI2SDE0LjczQzUuMjcsMzYuMjYsMCw0Mi44NiwwLDQ5Ljg5YzAsNi4zNyw0LjQsMTMuNjMsMTQuNzMsMTMuNjNoMzguOVoiLz48ZWxsaXBzZSBjbGFzcz0iY2xzLTIiIGN4PSI1My42MyIgY3k9IjQ5Ljg5IiByeD0iMTMuNDEiIHJ5PSIxMy42MyIvPjxwYXRoIGNsYXNzPSJjbHMtMSIgZD0iTTk0LjA3LDBINTVDNDUuNDksMCw0MC4yMiw2LjU5LDQwLjIyLDEzLjYzYzAsNi4zNyw0LjQsMTMuNjIsMTQuNzMsMTMuNjJoMzguOVYwWiIvPjxlbGxpcHNlIGNsYXNzPSJjbHMtMiIgY3g9Ijk0LjA3IiBjeT0iMTMuNjMiIHJ4PSIxMy40MSIgcnk9IjEzLjYzIi8%2BPC9zdmc%2B)](https://datastudio.google.com/reporting/3328e603-449b-4635-81a5-38e0abebafa4)

## USSD LIBRARY

### Implementations
Add the following dependency in your app's `build.gradle` configuration file:

| Repository | implementation | Status |
| :------: | ------ | :------: |
| jcenter() | 'com.romellfudi.ussdlibrary:ussd-library:1.1.i' | `DEPRECATED` |
| jcenter() | 'com.romellfudi.ussdlibrary:kotlin-ussd-library:1.1.k' | `DEPRECATED` |
| maven { url `https://jitpack.io` } | 'com.github.romellfudi.VoIpUSSD:ussd-library:1.4.a' | `READY` |
| maven { url `https://jitpack.io` } | 'com.github.romellfudi.VoIpUSSD:kotlin-ussd-library:1.4.a' | `READY` |

* Writing a config xml file from [here](https://github.com/romellfudi/VoIpUSSD/blob/master/ussd-library/src/main/res/xml/ussd_service.xml) `to res/xml` folder (if necessary), this config file allow to link between Application and System Oerative:

```xml
<?xml version="1.0" encoding="utf-8"?>
<accessibility-service xmlns:android="http://schemas.android.com/apk/res/android"
    .../>
```

### Application Permissions

To use the application, add the following permissions to your AndroidManifest.xml:

```xml
<uses-permission android:name="android.permission.CALL_PHONE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
```

### Service Configuration

Include the service in your AndroidManifest.xml for Java:

```xml
<service
    android:name="com.romellfudi.ussdlibrary.USSDService"
    android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE">
    <intent-filter>
        <action android:name="android.accessibilityservice.AccessibilityService" />
    </intent-filter>
    <meta-data
        android:name="android.accessibilityservice"
        android:resource="@xml/ussd_service" />
</service>
```

Or for Kotlin:

```xml
<service
    android:name="com.romellfudi.ussdlibrary.USSDServiceKT"
    android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE">
    <intent-filter>
        <action android:name="android.accessibilityservice.AccessibilityService" />
    </intent-filter>
    <meta-data
        android:name="android.accessibilityservice"
        android:resource="@xml/ussd_service" />
</service>
```

 **NOTE:** You may want to override ``android:notificationTimeout="0"``to a corresponding value of 200 or avobe by copying the contents of ``@xml/ussd_service`` to the xml dir.
 Otherwise it is possible that the library will miss accessibility events during operation, resulting is unintended behaivours (e.g not reading up to date prompts [#115](https://github.com/romellfudi/VoIpUSSD/issues/115) )

### Usage Instructions

1. Create a HashMap to identify USSD response messages for login and error scenarios:

| KEY MESSAGE | String Messages |
| ------ | ------ |
| KEY_LOGIN | "espere", "waiting", "loading", "esperando", ... |
| KEY_ERROR | "problema", "problem", "error", "null", ... |

For Java:

```java
Map<String, HashSet<String>> map = new HashMap<>();
map.put("KEY_LOGIN", new HashSet<>(Arrays.asList("espere", "waiting", "loading", "esperando")));
map.put("KEY_ERROR", new HashSet<>(Arrays.asList("problema", "problem", "error", "null")));
```

For Kotlin:

```kotlin
val map = HashMap<String, HashSet<String>>()
map["KEY_LOGIN"] = hashSetOf("espere", "waiting", "loading", "esperando")
map["KEY_ERROR"] = hashSetOf("problema", "problem", "error", "null")
```

2. Instantiate a USSDController object and invoke a USSD code:

For Java:

```java
USSDApi ussdApi = USSDController.getInstance(context);
ussdApi.callUSSDInvoke(phoneNumber, map, new USSDController.CallbackInvoke() {
    @Override
    public void responseInvoke(String message) {
        // Handle the USSD response
        String dataToSend = "data"; // Data to send to USSD
        ussdApi.send(dataToSend, new USSDController.CallbackMessage() {
            @Override
            public void responseMessage(String message) {
                // Handle the message from USSD
            }
        });
    }

    @Override
    public void over(String message) {
        // Handle the final message from USSD or error
    }
});
```

For Kotlin:

```kotlin
val ussdApi = USSDController.getInstance(context)
ussdApi.callUSSDOverlayInvoke(phoneNumber, map, object : USSDController.CallbackInvoke {
    override fun responseInvoke(message: String) {
        // Handle the USSD response
        val dataToSend = "data" // Data to send to USSD
        ussdApi.send(dataToSend) { responseMessage ->
            // Handle the message from USSD
        }
    }

    override fun over(message: String) {
        // Handle the final message from USSD or error
    }
})
```

3. For custom message handling, structure your code as follows:

For Java:

```java
// Example of selecting options from USSD menu
ussdApi.callUSSDInvoke(phoneNumber, map, new USSDController.CallbackInvoke() {
    ...
    // Select the first option
    ussdApi.send("1", new USSDController.CallbackMessage() {
        ...
        // Select the next option
        ussdApi.send("1", new USSDController.CallbackMessage() {
            ...
        });
    });
    ...
});
```

For Kotlin:

```kotlin
// Example of selecting options from USSD menu
ussdApi.callUSSDOverlayInvoke(phoneNumber, map, object : USSDController.CallbackInvoke {
    ...
    // Select the first option
    ussdApi.send("1") {
        ...
        // Select the next option
        ussdApi.send("1") {
            ...
        }
    }
    ...
})
```

4. For dual SIM support, specify the SIM slot:

For Java:

```java
ussdApi.callUSSDInvoke(phoneNumber, simSlot, map, new USSDController.CallbackInvoke() {
    ...
});
```

For Kotlin:

```kotlin
ussdApi.callUSSDOverlayInvoke(phoneNumber, simSlot, map, object : USSDController.CallbackInvoke {
    ...
});
```

### Static Methods

For Android M and above, check permissions before invoking USSD:

```java
// Check if accessibility permissions are enabled
ussdApi.verifyAccessibilityAccess(Activity);
// Check if overlay permissions are enabled
ussdApi.verifyOverlay(Activity);
```

### Overlay Service Widget (Optional)

For Android O and above, use the OverlayShowingService to handle overlay permissions. Add the following permission to your AndroidManifest.xml:

```xml
<uses-permission android:name="android.permission.ACTION_MANAGE_OVERLAY_PERMISSION" />
```

#### SplashLoadingService

Add the service to your AndroidManifest.xml:

```xml
<service android:name="com.romellfudi.ussdlibrary.SplashLoadingService"
         android:exported="false" />
```

Start and stop the service around the USSD invocation:

For Java:

```java
Intent svc = new Intent(activity, SplashLoadingService.class);
// Show the overlay
activity.startService(svc);
// Invoke USSD and handle responses
...
// Dismiss the overlay
activity.stopService(svc);
```

For Kotlin:

```kotlin
val svc = Intent(activity, SplashLoadingService::class.java)
// Show the overlay
activity.startService(svc)
// Invoke USSD and handle responses
...
// Dismiss the overlay
activity.stopService(svc)
```

<p align="center"> <img src="/snapshot/device_splash.gif" alt="Jitpack">  </p>

### EXTRA: Use Voip line

In this section leave the lines to call to Telcom (ussd number) for connected it:

```java
ussdPhoneNumber = ussdPhoneNumber.replace("#", uri);
Uri uriPhone = Uri.parse("tel:" + ussdPhoneNumber);
context.startActivity(new Intent(Intent.ACTION_CALL, uriPhone));
```

```kotlin
ussdPhoneNumber = ussdPhoneNumber.replace("#", uri)
val uriPhone = Uri.parse("tel:$ussdPhoneNumber")
context.startActivity(Intent(Intent.ACTION_CALL, uri))
```

Once initialized the call will begin to receive and send the **famous USSD windows**

## Cordova Plugin

* **cordova-plugin-VoIpUSSD** - [Ramy Mokako](https://github.com/rmxakalogistik/cordova-plugin-VoIpUSSD#cordova-plugin-voipussd)

## Contributors ✨
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![All Contributors](https://img.shields.io/badge/all_contributors-6-orange.svg?style=flat-square)](#contributors-)
<!-- ALL-CONTRIBUTORS-BADGE:END -->
Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key)):

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tr>
    <td align="center"><a href="https://github.com/romellfudi"><img src="https://avatars2.githubusercontent.com/u/1668017?v=4" width="100px;" alt=""/><br /><sub><b>Romell D.Z. 福迪</b></sub></a><br /><a href="https://github.com/romellfudi/VoIpUSSD/commits?author=romellfudi" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/abdullahicyc"><img src="https://avatars3.githubusercontent.com/u/15052571?v=4" width="100px;" alt=""/><br /><sub><b>Abdullahi Yusuf</b></sub></a><br /><a href="https://github.com/romellfudi/VoIpUSSD/commits?author=abdullahicyc" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/IMoHaMeDHaMdYI"><img src="https://avatars3.githubusercontent.com/u/36090360?v=4" width="100px;" alt=""/><br /><sub><b>Mohamed Hamdy Hasan</b></sub></a><br /><a href="https://github.com/romellfudi/VoIpUSSD/commits?author=IMoHaMeDHaMdYI" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/ducaale"><img src="https://avatars3.githubusercontent.com/u/16341131?v=4" width="100px;" alt=""/><br /><sub><b>Mohamed Daahir</b></sub></a><br /><a href="https://github.com/romellfudi/VoIpUSSD/commits?author=ducaale" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/rmxakalogistik"><img src="https://avatars3.githubusercontent.com/u/263449?v=4" width="100px;" alt=""/><br /><sub><b>Ramy Mokako</b></sub></a><br /><a href="https://github.com/rmxakalogistik/cordova-plugin-VoIpUSSD" title="Plugin">🔌</a></td>
    <td align="center"><a href="https://github.com/shadiqaust"><img src="https://avatars.githubusercontent.com/u/10364016?v=4" width="100px;" alt=""/><br /><sub><b>Md Mafizur Rahman</b></sub></a><br /><a href="https://github.com/romellfudi/VoIpUSSD/commits?author=shadiqaust" title="Code">💻</a></td>
  </tr>
</table>

<!-- markdownlint-enable -->
<!-- prettier-ignore-end -->
<!-- ALL-CONTRIBUTORS-LIST:END -->

## License
[![GNU GPLv3 Image](https://www.gnu.org/graphics/gplv3-127x51.png)](http://www.gnu.org/licenses/gpl-3.0.en.html)

VoIpUSSD is a Free Library Software: You can use, study share and improve it at your
will. Specifically you can redistribute and/or modify it under the terms of the
[GNU General Public License](https://www.gnu.org/licenses/gpl.html) as
published by the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.  
