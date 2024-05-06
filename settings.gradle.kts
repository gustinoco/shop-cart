pluginManagement {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
        mavenLocal()
        google()
        mavenCentral()
    }
}

include(":app")
include(":cart")