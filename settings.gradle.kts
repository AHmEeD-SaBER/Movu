pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    includeBuild("build-logic")
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Movu"
include(":app")
include(":core-ui")
include(":core-data")
include(":core-domain")
include(":authentication:data")
include(":authentication:domain")
include(":authentication:ui")
include(":firebase")
include(":splash")
include(":splash:data")
include(":splash:domain")
include(":splash:ui")
include(":navigation")
include(":home")
include(":home:data")
include(":home:domain")
include(":home:ui")
