# Twinsprite Android SDK Demo

This repository includes an Android Studio project with a demo APP that illustrates the main Twinsprite SDK methods. The Twinsprite Android SDK module is included inside the TwinspriteSDK folder.

The project also illustrates how to use the NFC reader of a Android phone to read a ToyxID and how to extract it from a QR code using the [ZXing Barcode Scanner](https://play.google.com/store/apps/details?id=com.google.zxing.client.android)


## Setup

1. If you don't have a [Twinsprite Development Portal](http://devportal.twinsprite.com/) account, follow the [Quick Start Guide](http://developers.twinsprite.com/v2/docs/pages/quickstart.html) to create an account, a game and some development Toyx.

2. Open the Android Studio project and replace the <b>API_KEY</b> and <b>SECRET_KEY</b> strings at the top of [MainActivity.java](https://github.com/twisprite-developers/twinsprite-android-demo/blob/master/app/src/main/java/com/twinsprite/test/MainActivity.java) with your game key-pair.

3. Build the application and run it on an Android Phone or the Android emulator. Download and install the [ZXing Barcode Scanner App](https://play.google.com/store/apps/details?id=com.google.zxing.client.android), requied to scan QR codes, scan a valid toyxID QR code from the [Twinsprite Development Portal](http://devportal.twinsprite.com/) and start making requests.

![image](http://developers.twinsprite.com/images/android-demo-app.png)

## NFC

If you have a Android device with NFC capabilities, store the ToyxID on a NFC tag using [NDEF](http://members.nfc-forum.org/specs/spec_list/#ndefts) format. You can use the [NXP TagWriter App](https://play.google.com/store/apps/details?id=com.nxp.nfc.tagwriter). It provides a function to write NFC tags directly from QR codes, so you can scan the ToyxID QR included on the portal.

## Code Overview

* [MainActivity.java](https://github.com/twisprite-developers/twinsprite-android-demo/blob/master/app/src/main/java/com/twinsprite/test/MainActivity.java) provides code examples of how to create a session, retrieve and update the Toyx data, and illustrates how to comunicate with the ZXing QR scanner.

* [NfcActivity.java](https://github.com/twisprite-developers/twinsprite-android-demo/blob/master/app/src/main/java/com/twinsprite/test/nfc/NfcActivity.java) illustrates how to init the Android NFC adapter and extract the NDEF formated tag with the toyxID.



## Resources

[Twinsprite Android SDK Documentation Site](http://developers.twinsprite.com/v2/docs/pages/android/quickstart.html)

[Twinsprite Android SDK Reference](http://developers.twinsprite.com/v2/sdk/android/index.html)

[Twinsprite Android SDK Module](http://developers.twinsprite.com/v2/downloads/android/TwinspriteSDK.aar)



Support: [dev@twinsprite.com](mailto:dev@twinsprite.com)
