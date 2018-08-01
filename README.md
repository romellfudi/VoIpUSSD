# Handler USSD windows API [![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14) [![platform](https://img.shields.io/badge/platform-android-brightgreen.svg)](https://developer.android.com/index.html)  [![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://github.com/whataa/pandora-no-op/blob/master/LICENSE) 

### by Romell Dominguez
[![](snapshot/icono.png)](https://www.romellfudi.com/)

## Target Development:

<style>
img[src*='#center'] { 
    width:500px;
    display: block;
    margin: auto;
}
img[src*='#gif'] { 
    width:200px;
    display: block;
    margin: auto;
}
</style>

![](snapshot/device_recored.gif#gif)


To comunicate with ussd display, It is necessary to have present that the interface depends on the SO and on the manufacturer of Android device.

## USSD LIBRARY

Build a accessibility service class:

![image](snapshot/G.png#center)

Capture information from USSD displaying windows, excist two ways:

* Writting code:

![image](snapshot/H.png#center)

* Writting xml, this link manifest to SO:

```xml
<?xml version="1.0" encoding="utf-8"?>
<accessibility-service xmlns:android="http://schemas.android.com/apk/res/android"
    android:accessibilityEventTypes
        ="typeWindowStateChanged"
    android:packageNames="com.android.phone"
    android:accessibilityFeedbackType="feedbackGeneric"
    android:accessibilityFlags="flagDefault"
    android:canRetrieveWindowContent="true"
    android:description="@string/accessibility_service_description"
    android:notificationTimeout="0"/>
```


### Application

Configure build.gradle file, add exteension for run aar libraries(witch we build and export)

```gradle
allprojects { repositories { ...
        flatDir { dirs 'libs' } } }
```

Configure ussd library dependencies on app module {debugCompile: attach library module, releaseCompile: import *.aar library}

```gradle
dependencies {
    ...
    debugCompile project(':ussdlibrary')
    releaseCompile(name: 'ussdlibrary-1.0.a', ext: 'aar')
}
```

Puts dependencies on manifest, into manifest put CALL_PHONE, READ_PHONE_STATE and SYSTEM_ALERT_WINDOW:

![image](snapshot/J.png#center)

![image](snapshot/F.png#center)

### Use Voip line

In this secction leave the lines to call to Telcom (ussd hadh number) for connected it:

```java
ussdPhoneNumber = ussdPhoneNumber.replace("#", uri);
Uri uriPhone = Uri.parse("tel:" + ussdPhoneNumber);
context.startActivity(new Intent(Intent.ACTION_CALL, uriPhone));
```

Once initialized the call will begin to receive and send the **famous USSD windows**

![image](snapshot/telcom.png#center)
