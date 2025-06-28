pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id ("org.jetbrains.kotlin.android") version "2.1.20" apply false
    }

}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "GuideExpert"
include(":app")
include(":feature:home")
include(":feature:favorites")
include(":feature:profile")

include(":core:domain")
include(":core:models")
include(":core:utils")
include(":core:design")
include(":core:data")
include(":core:database")
include(":core:network")
include(":core:notifications")
