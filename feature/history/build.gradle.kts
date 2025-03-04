plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.github.lucascalheiros.waterreminder.feature.history"
    compileSdk = Configs.COMPILE_SDK

    defaultConfig {
        minSdk = Configs.MIN_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = Configs.compileJavaVersion
        targetCompatibility = Configs.targetJavaVersion
    }
    kotlinOptions {
        jvmTarget = Configs.JVM_TARGET
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(projects.common.appCore)
    implementation(projects.common.ui)
    implementation(projects.common.util)
    implementation(projects.domain.history)
    implementation(projects.domain.measureSystem)
    implementation(projects.domain.waterManagement)
    implementation(libs.bundles.feature)
    testImplementation(libs.bundles.test)
    testImplementation(projects.test.feature)
    androidTestImplementation(libs.bundles.androidTest)
}