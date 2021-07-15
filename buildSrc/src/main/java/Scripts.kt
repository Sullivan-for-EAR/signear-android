import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.initialization.dsl.ScriptHandler
import org.gradle.kotlin.dsl.ScriptHandlerScope
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories

// buildscript
fun ScriptHandler.addScriptRepository() {
    repositories {
        addScriptDependencies()
    }
}

// allprojects
fun Project.addScriptRepository() {
    repositories {
        addScriptDependencies()
    }
}

private fun RepositoryHandler.addScriptDependencies() {
    google()
    jcenter()
    mavenCentral()
    google {
        content {
            includeGroupByRegex("com.android.*")
            includeGroupByRegex("androidx.*")
            includeGroupByRegex("com.google.*")
        }
    }
    maven("https://plugins.gradle.org/m2/") {
        content {
            includeGroup("org.jlleitschuh.gradle")
        }
    }
    maven("https://storage.googleapis.com/r8-releases/raw")
    maven("https://jitpack.io/")
}

fun ScriptHandlerScope.addScriptDependencies() {
    dependencies {
        classpath(Dep.GradlePlugin.android)
        classpath(Dep.GradlePlugin.kotlin)
        classpath(Dep.GradlePlugin.kotlinSerialization)
        classpath(Dep.GradlePlugin.ktlint)
        classpath(Dep.GradlePlugin.hilt)
        classpath(Dep.GradlePlugin.navSafeArgPlugin)
        classpath(Dep.GradlePlugin.sentry)
        classpath(Dep.GradlePlugin.r8)
    }
}
