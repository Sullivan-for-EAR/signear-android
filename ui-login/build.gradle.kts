plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-android")
}

listOf(
    "commonConfiguration.gradle",
    "libraryConfiguration.gradle"
).forEach { file ->
    apply(from = "${rootProject.projectDir}/gradle/${file}")
}

android {
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(":ui-common"))

    // kotlin
    implementation(Dep.Kotlin.stdlibJvm)
    implementation(Dep.Kotlin.coroutines.core)
    implementation(Dep.Kotlin.coroutines.android)

    implementation(Dep.AndroidX.activity.ktx)
    implementation(Dep.AndroidX.lifecycle.viewModelKtx)
    implementation(Dep.AndroidX.lifecycle.liveDataKtx)
    implementation(Dep.AndroidX.UI.material)

    // Hilt
    implementation(Dep.Dagger.Hilt.android)
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt(Dep.Dagger.Hilt.compiler)

    implementation(Dep.AndroidX.Navigation.fragmentKtx)
    implementation(Dep.AndroidX.Navigation.uiKtx)

    implementation(Dep.timber)
}

kapt {
    useBuildCache = true
}