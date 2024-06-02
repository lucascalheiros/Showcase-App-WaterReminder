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
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "WaterReminderMVP-sample"
include(":app")

include(":common")
include(":common:appCore")
include(":common:util")
include(":common:ui")

include(":domain")
include(":domain:remindNotifications")
include(":domain:waterManagement")
include(":domain:userInformation")
include(":domain:measureSystem")

include(":feature")
include(":feature:home")
include(":feature:settings")
include(":feature:history")

include(":data")
include(":data:notificationProvider")
include(":data:themeWrapper")
