# Changelog

## Release USSD Library 1.1.i Interface for usage (06/03/2019)
- Overlay permissions moves into library and have static methods
---

## Release USSD Library 1.1.h Interface for usage (13/02/2019)
- Avoid issues when uses the reference: use USSDApi instead USSDController
---

## Release USSD Library 1.1.g Support multi-slot (23/01/2019)
- Release support multiple sim slots & increase minSDK to 23
---

## Release USSD Library 1.1.e Support for ITEL devices (21/01/2019)
- Release context instead of activity & support for itel devices
---

## Release USSD Library 1.1.d Splash Overlay (27/10/2018)
- Release adding second Overlay using full device screen, don't show any flashing window
---

## Release USSD Library 1.1.c Mapping Message (04/10/2018)
- Release using mapping from detect login and error pages
---

## Release USSD Android Library 1.1.b (27/09/2018)
New Structure of Messaging, reading and sending for each message

latestVersion is 1.1.b

repositories {
    jcenter()
}
dependencies {
    implementation 'com.romellfudi.ussdlibrary:ussd-library:{latestVersion}'
}
---

## Release upload on Maven Central (15/09/2018)
Upload on Maven Central

repositories {
    jcenter()
}
dependencies {
    compile 'com.romellfudi.ussdlibrary:ussd-library:{latestVersion}'
}
---

## Release USSD Android Library 1.0.b (27/08/2018)
Upgrade con.romellfudi for Android Library.

- Import ussdlibrary-1.0.b.aar
- put ussd_service.xml into res/xml folder

or import into project:

maven {
        url 'https://dl.bintray.com/romllz489/maven/'
}
dependencies {
    compile 'com.romellfudi.ussdlibrary:ussd-library:{latestVersion}'
}
---

## Release USSD Android Library 1.0.a (31/07/2018)
Remember import the internal xml into aar or replace with xml similar to ussd_service.xml