plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

listOf(
    "commonConfiguration.gradle",
    "libraryConfiguration.gradle"
).forEach { file ->
    apply(from = "${rootProject.projectDir}/gradle/${file}")
}

dependencies {

    implementation(project(":common:core"))
    implementation(project(":common:ui-common"))

    implementation(Dep.Kotlin.stdlibJvm)
    implementation(Dep.Kotlin.coroutines.core)
    implementation(Dep.Kotlin.coroutines.android)

    implementation(Dep.Dagger.Hilt.android)
    kapt(Dep.Dagger.Hilt.compiler)

    implementation(Dep.Retrofit.retrofit)
    implementation(Dep.Retrofit.gson)
    implementation(Dep.Retrofit.converterGson)
    implementation(Dep.OkHttp.core)
    implementation(Dep.OkHttp.loggingInterceptor)

    coreLibraryDesugaring (Dep.Tool.desugarJdk)
}

kapt {
    useBuildCache = true
    mapDiagnosticLocations = true
    arguments {
        arg("dagger.formatGeneratedSource", "disabled")
        arg("dagger.fastInit", "enabled")
        arg("dagger.experimentalDaggerErrorMessages", "enabled")
    }
}