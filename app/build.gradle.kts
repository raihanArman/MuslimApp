plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.raydev.muslim_app"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        create("release") {
//            storeFile = file(RELEASE_STORE_FILE)
//            storePassword = RELEASE_STORE_PASSWORD
//            keyAlias = RELEASE_KEY_ALIAS
//            keyPassword = RELEASE_KEY_PASSWORD
            // Optional, specify signing versions used
//            v1SigningEnabled =  true
//            v2SigningEnabled = true
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
//            signingConfig = signingConfigs.release
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
//        applicationVariants.all{
//            variant ->
//                variant.outputs.each{
//                    output->
//                        project.ext { appName = "MuslimApp ${variant.versionName} (${variant.versionCode})" }
//                        def formattedDate = new Date().format('yyyy-MM-dd-HH-mm-ss')
//                        def newName = output.outputFile.name
//                        newName = newName.replace("app-", "$project.ext.appName-")
//                        newName = newName.replace("-debug", " - debug - " + formattedDate)
//                        newName = newName.replace("-release", " - release - " + formattedDate)
//                        output.outputFileName  = newName
//                }
//        }
    }

    flavorDimensions.add("dev")
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    productFlavors {
        create("dev") {
            applicationId = "com.raydev.muslim_app.dev"
        }
        create("state") {
            applicationId = "com.raydev.muslim_app.stage"
        }
        create("prod") {
            applicationId = "com.raydev.muslim_app"
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

//apply from: '../shared_dependencies.gradle'
dependencies {
    implementation(project(":libraries:shared_preference"))
    implementation(project(":libraries:abstract"))
    implementation(project(":libraries:network"))
    implementation(project(":libraries:cache"))
    implementation(project(":libraries:workmanager"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":feature:quran"))
    implementation(project(":feature:prayer"))

    implementation(libs.bundles.koin)
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation(project(":shared"))
    testImplementation("junit:junit:4.+")
    testImplementation(project(":libraries:abstract"))
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}