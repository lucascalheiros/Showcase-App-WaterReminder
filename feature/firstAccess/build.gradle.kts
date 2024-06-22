plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.github.lucascalheiros.waterreminder.feature.firstaccess"
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
    implementation(projects.common.ui)
    implementation(projects.common.util)
    implementation(projects.common.appCore)
    implementation(projects.common.permissionManager)
    implementation(projects.domain.measureSystem)
    implementation(projects.domain.userInformation)
    implementation(projects.domain.waterManagement)
    implementation(projects.domain.firstAccess)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.bundles.feature)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}