plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    compileSdk = 34
    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
}

dependencies {

    implementation(libs.bundles.compose)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.koin.compose)
    implementation(libs.bundles.maps)
    implementation(libs.androidx.work.manager)
    implementation(libs.androidx.glance)

    implementation(project(":domain"))
    implementation(project(":shared"))
    implementation(project(":libraries:abstract"))
    implementation(project(":libraries:workmanager"))
    implementation(project(":core:ui"))
    implementation(project(":feature:qibla"))
}
