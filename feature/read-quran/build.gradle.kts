plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.library)
    id("kotlin-kapt")
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 21

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
    implementation(libs.bundles.accompanist)
    implementation(libs.bundles.koin.compose)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.work.manager)
//    kapt(libs.androidx.room.compiler)
    implementation(project(":domain"))
    implementation(project(":libraries:abstract"))
    implementation(project(":libraries:workmanager"))
    implementation(project(":core:navigation"))
    implementation(project(":core:ui"))
//    implementation(libs.androidx.ui.text.android)
}
