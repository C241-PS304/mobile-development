# SkinTrack Mobile Development - Bangkit Capstone Project

This repo contains development code for the SkinTrack application. This application was created using Kotlin as the main language and uses Android Studio as the IDE.

Screenshots: 

## How to run
**1. Build from source code**
- Install Android Studio (minimum jellyfish)
- Clone this repository
- Open folder in android studio
- Build the app

**2. Install from release**
- Navigate to [release](https://github.com/C241-PS304/mobile-development/releases/tag/v1.0.0/SkinTrack.apk)
- Download the apk file and install in android devices

## Dependencies used in project
```
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.tensorflow.lite.support)
    implementation(libs.tensorflow.lite.metadata)
    implementation(libs.tensorflow.lite.gpu)
    implementation(libs.datastore.preferences)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Networking Library (Retrofit, Gson Converter, Http Interceptor)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Glide
    implementation(libs.glide)

    // CameraX
    implementation(libs.androidx.camera.camera2)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.view)

    // Firebase
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation (libs.firebase.auth)

    // Splash Screen API
    implementation(libs.androidx.core.splashscreen)

    // ViewPager2 and Indicator
    implementation(libs.androidx.viewpager2)
    implementation(libs.dotsindicator)
```

## Other resource
- Prototype Application using [Figma](https://www.figma.com/design/hsfndDwuHIUSkEsYJZHxTb/Prototype-[FaceTrack]?node-id=0-1&t=RNMFyAB9waDfhVWr-0)
- All assets that we used from [UnDraw](https://undraw.co/)
