plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(ProjectConfigurations.compileSdk)
    buildToolsVersion(ProjectConfigurations.buildTools)

    defaultConfig {
        applicationId = ProjectConfigurations.appId
        minSdkVersion(ProjectConfigurations.minSdk)
        targetSdkVersion(ProjectConfigurations.targetSdk)
        versionCode = ProjectConfigurations.versionCode
        versionName = ProjectConfigurations.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName(BuildType.DEBUG) {
            signingConfig = signingConfigs.getByName("debug")
            applicationIdSuffix = ".debug"
        }

        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles(getDefaultProguardFile("proguard-android.txt"))
            proguardFiles(file("proguard-rules.pro"))
        }
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(Dep.Kotlin.stdlibJvm)

    implementation(Dep.AndroidX.activity.ktx)
    implementation(Dep.AndroidX.lifecycle.viewModelKtx)
    implementation(Dep.AndroidX.StartUp.runtime)

    // Android UI
    implementation(Dep.AndroidX.UI.material)

    // Hilt
    implementation(Dep.Dagger.Hilt.android)
    kapt(Dep.Dagger.Hilt.compiler)

    // kotlin
    implementation(Dep.Kotlin.coroutines.core)
    implementation(Dep.Kotlin.coroutines.android)

    implementation(Dep.timber)
}

kapt {
    useBuildCache = true
}