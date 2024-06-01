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

rootProject.name = "EventFinderApp"
include(":app")
include(":feature")
include(":feature:auth")
include(":feature:auth:api")
include(":feature:auth:impl")
include(":common")
include(":feature:events")
include(":feature:events:api")
include(":feature:events:impl")
include(":feature:kudago")
include(":feature:kudago:api")
include(":feature:notes")
include(":feature:notes:api")
include(":feature:notes:impl")
include(":feature:profile")
include(":feature:profile:api")
include(":feature:profile:impl")
