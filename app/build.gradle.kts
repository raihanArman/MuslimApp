
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.*

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.raydev.muslim_app"
        minSdk = 23
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
//    signingConfigs {
//        create("release") {
//            storeFile = file(RELEASE_STORE_FILE)
//            storePassword = RELEASE_STORE_PASSWORD
//            keyAlias = RELEASE_KEY_ALIAS
//            keyPassword = RELEASE_KEY_PASSWORD
//             Optional, specify signing versions used
//            v1SigningEnabled =  true
//            v2SigningEnabled = true
//        }
//    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
//            signingConfig = signingConfigs.release
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        applicationVariants.all {
            this.outputs.forEach { output ->
                val cal = Calendar.getInstance()
                val dateFormat = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale("id"))
                val formattedDate = dateFormat.format(cal.time)

                var newName = output.outputFile.name
                newName = newName.replace("app-", "$project.ext.appName-")
                newName = newName.replace("-debug", " - debug - " + formattedDate)
                newName = newName.replace("-release", " - release - " + formattedDate)

                val originalApk = this@all.outputs.firstOrNull()?.outputFile
                val outputDir = originalApk?.parentFile
                val newApk = File(outputDir, newName)
                originalApk?.renameTo(newApk)

//                output.outputFile = originalApk
            }
        }
    }

    flavorDimensions.add("default")
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    productFlavors {
        val string = "String"
        val stringLower = "string"
        create("dev") {
            applicationId = DevConfigField.applicationId
            buildConfigField(string, "HERE_API_KEY", getLocalProperty("HERE_API_KEY").toString())
        }
        create("stage") {
            applicationId = StageConfigField.applicationId
            buildConfigField(string, "HERE_API_KEY", getLocalProperty("HERE_API_KEY").toString())
        }
        create("prod") {
            applicationId = ProdConfigField.applicationId
            buildConfigField(string, "HERE_API_KEY", getLocalProperty("HERE_API_KEY").toString())
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
}

// apply from: '../shared_dependencies.gradle'
dependencies {
    implementation(project(":libraries:shared_preference"))
    implementation(project(":libraries:abstract"))
    implementation(project(":libraries:network"))
    implementation(project(":libraries:cache"))
    implementation(project(":libraries:location"))
    implementation(project(":libraries:here-api"))
    implementation(project(":libraries:workmanager"))
    implementation(project(":core:navigation"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":feature:quran"))
    implementation(project(":feature:prayer"))
    implementation(project(":feature:main"))
    implementation(project(":feature:dashboard"))
    implementation(project(":feature:splash"))
    implementation(project(":feature:read-quran"))
    implementation(project(":feature:home"))
    implementation(project(":feature:bookmark"))
    implementation(project(":feature:qibla"))

    implementation(libs.bundles.maps)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.accompanist)
    implementation(libs.bundles.koin.compose)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(platform(libs.firebase.crashlystic))

//    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation(project(":shared"))
    testImplementation("junit:junit:4.+")
    testImplementation(project(":libraries:abstract"))
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

fun getLocalProperty(key: String): Any {
    val localProperties = Properties()
    localProperties.load(FileInputStream(rootProject.file("local.properties")))
    return localProperties[key] ?: ""
}
