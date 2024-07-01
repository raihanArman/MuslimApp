plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.raihanarman.main"
    compileSdk = 34

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    implementation(libs.bundles.accompanist)
    implementation(libs.bundles.maps)
    implementation(project(":domain"))
    implementation(project(":shared"))
    implementation(project(":core:navigation"))
    implementation(project(":libraries:abstract"))
    implementation(project(":libraries:workmanager"))
    implementation(project(":libraries:location"))
    implementation(project(":feature:read-quran"))
    implementation(project(":feature:bookmark"))
    implementation(project(":feature:dashboard"))
    implementation(project(":feature:home"))
    implementation(project(":feature:quran"))
    implementation(project(":feature:prayer"))
    implementation(project(":feature:dailyduas"))
    implementation(project(":feature:dzikir"))
    implementation(project(":feature:player"))
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}
