plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.github.lucascalheiros.waterreminder.feature.settings"
    compileSdk = Configs.compileSdk

    defaultConfig {
        minSdk = Configs.minSdk

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(projects.common.appCore)
    implementation(projects.common.ui)
    implementation(projects.common.util)
    implementation(projects.domain.measureSystem)
    implementation(projects.domain.waterManagement)
    implementation(projects.domain.remindNotifications)
    implementation(projects.domain.userInformation)
    implementation(libs.bundles.feature)
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.androidTest)
}