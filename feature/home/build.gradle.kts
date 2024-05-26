plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.github.lucascalheiros.waterremindermvp.feature.home"
    compileSdk = Configs.compileSdk

    defaultConfig {
        minSdk = Configs.minSdk

        testInstrumentationRunner = "com.github.lucascalheiros.waterremindermvp.feature.home.InstrumentationTestRunner"
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
    implementation(projects.common.measureSystem)
    implementation(projects.domain.waterManagement)
    implementation(libs.bundles.feature)
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.featureAndroidTest)
}