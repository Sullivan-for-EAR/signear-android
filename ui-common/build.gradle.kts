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

android {
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(Dep.Kotlin.stdlibJvm)
    implementation(Dep.AndroidX.activity.ktx)
    implementation(Dep.AndroidX.lifecycle.viewModelKtx)
    implementation(Dep.AndroidX.lifecycle.commonJava8)

    // Android UI
    implementation(Dep.AndroidX.UI.material)

    implementation(Dep.AndroidX.StartUp.runtime)
    implementation(Dep.AndroidX.Navigation.fragmentKtx)
    implementation(Dep.AndroidX.Navigation.uiKtx)

    implementation(Dep.timber)

    coreLibraryDesugaring (Dep.Tool.desugarJdk)
}
