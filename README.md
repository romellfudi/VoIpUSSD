# Handler USSD API

[![Platform](https://img.shields.io/badge/platform-android-brightgreen.svg)](https://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=23)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://github.com/romellfudi/VoIpUSSDSample/blob/master/LICENSE)
[![](https://github.com/romellfudi/VoIpUSSD/workflows/Android%20CI/badge.svg)](https://github.com/romellfudi/VoIpUSSD/actions)
[![Gitter](https://badges.gitter.im/romellfudi/VoIpUSSD.svg)](https://gitter.im/romellfudi/VoIpUSSD?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
[![Bintray](https://img.shields.io/bintray/v/romllz489/maven/ussd-library.svg)](https://bintray.com/romllz489/maven/ussd-library)
[![Bintray Kotlin](https://img.shields.io/bintray/v/romllz489/maven/kotlin-ussd-library.svg)](https://bintray.com/romllz489/maven/kotlin-ussd-library)
[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-Void%20USSD%20Library-green.svg?style=flat )]( https://android-arsenal.com/details/1/7151 )
[![Jitpack](https://jitpack.io/v/romellfudi/VoIpUSSD.svg)](https://jitpack.io/#romellfudi/VoIpUSSD)
[![](https://img.shields.io/badge/language-ES-blue.svg)](./README.es)

### by Romell Dominguez
[![](https://raw.githubusercontent.com/romellfudi/assets/master/favicon.ico)](https://www.romellfudi.com/)

## Target Development [High Quality](https://raw.githubusercontent.com/romellfudi/VoIpUSSD/Rev04/snapshot/device_recored.gif):

![](snapshot/device_recored.gif#gif)

Interactive with ussd windoows, It is necessary to have present that the interface depends on the SO and on the manufacturer of Android device.

## Community Chat
- Join us [![Gitter](https://badges.gitter.im/romellfudi/VoIpUSSD.svg)](https://gitter.im/romellfudi/VoIpUSSD?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

## USSD LIBRARY

`latestVersion` is ![](https://img.shields.io/bintray/v/romllz489/maven/ussd-library.svg)

`kotlinLatestVersion` is ![](https://img.shields.io/bintray/v/romllz489/maven/kotlin-ussd-library.svg)

Add the following in your app's `build.gradle` file:

```groovy
repositories {
    jcenter()
    maven { url 'https://jitpack.io' }// (jetpacking)
}
dependencies {
    // java
    implementation 'com.romellfudi.ussdlibrary:ussd-library:{latestVersion}'
    // kotlin
    implementation 'com.romellfudi.ussdlibrary:kotlin-ussd-library:{kotlinLatestVersion}'
    // alternative SNAPSHOT (jetpacking)
    implementation 'com.github.romellfudi.VoIpUSSD:ussd-library:alternative-SNAPSHOT'
    implementation 'com.github.romellfudi.VoIpUSSD:kotlin-ussd-library:alternative-SNAPSHOT'
}
```

if you use Kotlin please check my other project (kotlin branch is deprecated): [`Kotlin Void USSD`](https://github.com/romellfudi/KotlinVoIpUSSD)

* Writing xml config file from [here](https://github.com/romellfudi/VoIpUSSD/blob/master/ussd-library/src/main/res/xml/ussd_service.xml) to res/xml folder (if necessary), this config file allow link between App and SO:

```xml
<?xml version="1.0" encoding="utf-8"?>
<accessibility-service xmlns:android="http://schemas.android.com/apk/res/android"
    .../>
```

### Application

Puts dependencies on manifest, into manifest put CALL_PHONE, READ_PHONE_STATE and SYSTEM_ALERT_WINDOW:

```xml
    <uses-permission android:name="android.permission.CALL_PHONE" />
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
```

Add service:

```xml
java
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

```xml
kotlin
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

# How use:

First you need an hashMap from detect witch USSD' response contains the login and error messages

| KEY MESSAGE | String Messages |
| ------ | ------ |
| KEY_LOGIN | "espere","waiting","loading","esperando",... |
| KEY_ERROR | "problema","problem","error","null",... |

```java
map = new HashMap<>();
map.put("KEY_LOGIN",new HashSet<>(Arrays.asList("espere", "waiting", "loading", "esperando")));
map.put("KEY_ERROR",new HashSet<>(Arrays.asList("problema", "problem", "error", "null")));
```

```kotlin
map = HashMap()
map!!["KEY_LOGIN"] = HashSet(Arrays.asList("espere", "waiting", "loading", "esperando"))
map!!["KEY_ERROR"] = HashSet(Arrays.asList("problema", "problem", "error", "null"))
```

Instance an object ussController with context

```java
USSDApi ussdApi = USSDController.getInstance(context);
ussdApi.callUSSDInvoke(phoneNumber, map, new USSDController.CallbackInvoke() {
    @Override
    public void responseInvoke(String message) {
        // message has the response string data
        String dataToSend = "data"// <- send "data" into USSD's input text
        ussdApi.send(dataToSend,new USSDController.CallbackMessage(){
            @Override
            public void responseMessage(String message) {
                // message has the response string data from USSD
            }
        });
    }

    @Override
    public void over(String message) {
        // message has the response string data from USSD or error
        // response no have input text, NOT SEND ANY DATA
    }
});
```

```kotlin
USSDApi ussdApi = USSDController.getInstance(activity!!)
ussdApi.callUSSDOverlayInvoke(phoneNumber, map!!, object : USSDController.CallbackInvoke {
    override fun responseInvoke(message: String) {
        // message has the response string data
        var dataToSend = "data"// <- send "data" into USSD's input text
        ussdApi!!.send("1") { // it: response String
                // message has the response string data from USSD
        }
     }

    override fun over(message: String) {
        // message has the response string data from USSD or error
        // response no have input text, NOT SEND ANY DATA
    }
})
```

if you need work with your custom messages, use this structure:

```java
ussdApi.callUSSDInvoke(phoneNumber, map, new USSDController.CallbackInvoke() {
    @Override
    public void responseInvoke(String message) {
        // first option list - select option 1
        ussdApi.send("1",new USSDController.CallbackMessage(){
            @Override
            public void responseMessage(String message) {
                // second option list - select option 1
                ussdApi.send("1",new USSDController.CallbackMessage(){
                    @Override
                    public void responseMessage(String message) {
                        ...
                    }
                });
            }
        });
    }

    @Override
    public void over(String message) {
        // message has the response string data from USSD
        // response no have input text, NOT SEND ANY DATA
    }
    ...
});
```

```kotlin
ussdApi.callUSSDOverlayInvoke(phoneNumber, map!!, object : USSDController.CallbackInvoke {
    override fun responseInvoke(message: String) {
        // first option list - select option 1
        ussdApi!!.send("1") { // it: response response
                // second option list - select option 1
                ussdApi.send("1") { // it: response message
                        ...
                }
        }
    }

    override fun over(message: String) {
        // message has the response string data from USSD
        // response no have input text, NOT SEND ANY DATA
    }
    ...
})
```

for dual sim support

```java
ussdApi.callUSSDInvoke(phoneNumber, simSlot, map, new USSDController.CallbackInvoke() {
    ...
}
```

```kotlin
ussdApi.callUSSDOverlayInvoke(phoneNumber, simSlot, map!!, object : USSDController.CallbackInvoke {
    ...
}
```

## Static Methods
In case use at android >= M, you could check previusly permissions, `callInvoke` and `callUSSDOverlayInvoke` methods check eneble too:

```java
 # check if accessibility permissions is enable
    USSDController.verifyAccesibilityAccess(Activity)
 # check if overlay permissions is enable
    USSDController.verifyOverLay(Activity)
```

## Overlay Service Widget (not required)

A huge problem working with ussd is you can not invisible, disenable, resize or put on back in progressDialog
But now on Android O, Google allow build a nw kind permission from overlay widget, my solution was a widget call OverlayShowingService:
For use need add permissions at AndroidManifest:

```xml
<uses-permission android:name="android.permission.ACTION_MANAGE_OVERLAY_PERMISSION" />
```

Using the library you could use two ways:

### SplashLoadingService

Add Broadcast Service:

```xml
<service android:name="com.romellfudi.ussdlibrary.SplashLoadingService"
         android:exported="false" />
```

Invoke like a normal service:

```java
svc = new Intent(activity, SplashLoadingService.class);
// show layout
getActivity().startService();
ussdApi.callUSSDOverlayInvoke(phoneNumber, simSlot, map, new USSDController.CallbackInvoke() {
        ...
        // dismiss layout
        getActivity().stopService(svc);
        ...
}
```

```kotlin
svc = Intent(activity, OverlayShowingService::class.java)
// show layout
activity.startService(svc)
ussdApi.callUSSDOverlayInvoke(phoneNumber, map!!, object : USSDController.CallbackInvoke {
        ...
        // dismiss layout
        activity.stopService(svc)
        ...
}
```

![](snapshot/device_splash.gif#gif)

## Cordova Plugin

* **cordova-plugin-VoIpUSSD** - [Ramy Mokako](https://github.com/rmxakalogistik/cordova-plugin-VoIpUSSD#cordova-plugin-voipussd)

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

### License
```
Copyright 2017 Romell D.Z.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

<style>
img[src*='#center'] { 
    width:390px;
    display: block;
    margin: auto;
}
img[src*='#gif'] { 
    width:200px;
    display: block;
    margin: auto;
}
</style>
