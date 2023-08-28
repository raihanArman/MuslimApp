// Top-level build file where you can add configuration options common to all sub-projects/modules.
//@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlin.android).apply(false)
    alias(libs.plugins.kotlin.jvm).apply(false)
}

tasks.wrapper {
    gradleVersion = "7.3.3"
    distributionType = Wrapper.DistributionType.ALL
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        val nav_version = "2.3.5"
        val ktlintPluginVersion = "10.2.0"
        val detektVersion = "1.17.0"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:$ktlintPluginVersion")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$detektVersion")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

apply(from="buildScripts/git-hooks.gradle")

subprojects {
    apply {
        from("$rootDir/buildScripts/ktlint.gradle")
    }
    apply {
        from("$rootDir/buildScripts/detekt.gradle")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
//versionCatalogUpdate {
//    versionCatalogs{
//        create("libs"){
//            catalogFile.set(file("gradle/libs/libs.versions.toml"))
//            keep.keepUnusedVersions.set(true)
//        }
//    }
//}