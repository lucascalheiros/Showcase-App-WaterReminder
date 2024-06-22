plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.github.lucascalheiros.waterreminder.feature.home"
    compileSdk = Configs.COMPILE_SDK

    defaultConfig {
        minSdk = Configs.MIN_SDK

        testInstrumentationRunner = "com.github.lucascalheiros.waterreminder.feature.home.InstrumentationTestRunner"
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }


}

dependencies {
    implementation(projects.common.appCore)
    implementation(projects.common.util)
    implementation(projects.common.ui)
    implementation(projects.domain.measureSystem)
    implementation(projects.domain.waterManagement)
    implementation(libs.bundles.feature)
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.featureAndroidTest)
}