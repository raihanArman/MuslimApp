plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    compileSdk = 33
    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":shared"))
    implementation(project(":libraries:cache"))
    implementation(project(":libraries:abstract"))
    implementation(project(":libraries:prayer"))
    implementation(project(":libraries:shared_preference"))
    implementation(project(":libraries:location"))

    implementation(libs.bundles.koin)
    implementation(libs.bundles.retrofit)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    implementation(libs.gson)
    implementation(libs.coroutines.android)
    implementation(libs.bundles.maps)
    implementation("com.github.msarhan:ummalqura-calendar:2.0.2")
}