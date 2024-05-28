plugins {
    id("com.android.library")
    id("kotlin-android")
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
    implementation(libs.bundles.accompanist)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.koin.compose)
}
