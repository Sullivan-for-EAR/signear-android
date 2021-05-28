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

        vectorDrawables.useSupportLibrary = true
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

    testOptions {
        animationsDisabled = true
        unitTests.apply {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }

    packagingOptions {
        exclude("**/*.kotlin_module")
        exclude("**/*.version")
        exclude("**/kotlin/**")
        exclude("**/*.txt")
        exclude("**/*.xml")
        exclude("**/*.properties")
    }
}

dependencies {

    implementation(project(":common:core"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":common:ui-common"))
    implementation(project(":ui-login"))
    implementation(project(":ui-reservation"))

    implementation(Dep.Kotlin.stdlibJvm)
    implementation(Dep.Kotlin.coroutines.core)
    implementation(Dep.Kotlin.coroutines.android)

    implementation(Dep.AndroidX.activity.ktx)
    implementation(Dep.AndroidX.lifecycle.viewModelKtx)
    implementation(Dep.AndroidX.lifecycle.liveDataKtx)
    implementation(Dep.AndroidX.StartUp.runtime)
    implementation(Dep.AndroidX.UI.material)

    implementation(Dep.Dagger.Hilt.android)
    kapt(Dep.Dagger.Hilt.compiler)

    implementation(Dep.OkHttp.core)
    implementation(Dep.Retrofit.retrofit)
    
    implementation(Dep.timber)
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