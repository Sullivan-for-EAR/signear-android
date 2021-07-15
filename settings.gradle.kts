dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
}

rootProject.name = "signear-android"

include(
    ":app",
    ":data",
    ":common:core",
//    ":core-android",
//    ":model",
    ":domain",
//    ":site",
    ":common:ui-common",
    ":ui-login",
    ":ui-reservation"
//    ":ui-weekly",
//    ":ui-episode",
//    ":ui-setting",
//    ":ui-detail",
//    ":compose"
)
