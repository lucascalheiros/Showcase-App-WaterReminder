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

rootProject.name = "WaterReminderMVP-sample"
include(":app")
include(":common")
include(":common:util")
include(":common:ui")
include(":domain")
include(":domain:remindNotifications")
include(":domain:waterManagement")
include(":feature")
include(":feature:home")
include(":feature:settings")
include(":feature:history")

