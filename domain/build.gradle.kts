plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    compileSdk = 33
//
    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":libraries:abstract"))
    implementation(project(":libraries:prayer"))
    implementation(project(":libraries:location"))
    implementation(libs.bundles.koin)

    implementation(libs.gson)
    implementation(libs.coroutines.android)
    implementation(libs.bundles.maps)
}
