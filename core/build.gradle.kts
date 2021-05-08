plugins {
    id("com.android.library")
    kotlin("android")
}

listOf(
    "commonConfiguration.gradle",
    "libraryConfiguration.gradle"
).forEach { file ->
    apply(from = "${rootProject.projectDir}/gradle/${file}")
}

dependencies {

    implementation(Dep.Kotlin.stdlibJvm)

    coreLibraryDesugaring (Dep.Tool.desugarJdk)
}