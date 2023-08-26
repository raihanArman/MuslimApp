plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.library)
}

android {
    compileSdk = 33

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
    implementation(libs.androidx.room.runtime)
//    kapt(libs.androidx.room.compiler)
    implementation(project(":domain"))
    implementation(project(":shared"))
    implementation(project(":libraries:abstract"))
    implementation(project(":libraries:location"))
    implementation(project(":libraries:workmanager"))
    implementation(project(":core:navigation"))
    implementation(project(":core:ui"))
//    implementation(libs.androidx.ui.text.android)
}