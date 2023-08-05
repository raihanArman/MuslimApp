pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        jcenter()
        maven { url = uri("https://www.jitpack.io") }
        flatDir { dirs("app/libs") }

    }
}
dependencyResolutionManagement {
    enableFeaturePreview("VERSION_CATALOGS")

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://www.jitpack.io") }
        jcenter()
        flatDir { dirs("app/libs") }
    }
    versionCatalogs {
        create("libs") {
            from(files("gradle/libs/libs.versions.toml"))
        }
    }

}
rootProject.name = "My Application"
include(
    ":app",
    ":feature:prayer",
    ":feature:quran",
    ":feature:book",
    ":feature:home",
    ":data",
    ":domain",
    ":navigation",
    ":resource",
    ":libraries:abstract",
    ":libraries:network",
    ":libraries:cache",
    ":shared",
    ":cache",
    ":libraries:workmanager",
    ":libraries:shared_preference"
)
