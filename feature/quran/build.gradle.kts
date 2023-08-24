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
    implementation(libs.bundles.koin.compose)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.work.manager)
    kapt(libs.androidx.room.compiler)
    implementation(project(":domain"))
    implementation(project(":shared"))
    implementation(project(":libraries:abstract"))
    implementation(project(":libraries:workmanager"))
    implementation(project(":core:navigation"))
//    implementation project(path: ':resource')
//
//    implementation 'androidx.core:core-ktx:1.7.0'
//    implementation 'androidx.appcompat:appcompat:1.4.1'
//    implementation 'com.google.android.material:material:1.5.0'
//    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
//    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
//    testImplementation 'junit:junit:4.+'
//    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
//    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
}