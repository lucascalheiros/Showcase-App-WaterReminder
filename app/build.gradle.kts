plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "com.github.lucascalheiros.waterreminder"
    compileSdk = Configs.compileSdk

    defaultConfig {
        applicationId = Configs.applicationId
        minSdk = Configs.minSdk
        targetSdk = Configs.targetSdk
        versionCode = Configs.versionCode
        versionName = Configs.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        buildConfig = true
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
    implementation(platform(libs.firebase.bom))
    implementation(projects.common.util)
    implementation(projects.common.ui)
    implementation(projects.common.appCore)
    implementation(projects.data.notificationProvider)
    implementation(projects.data.firstAccessDataProvider)
    implementation(projects.data.themeProvider)
    implementation(projects.data.userProfileProvider)
    implementation(projects.data.waterDataProvider)
    implementation(projects.domain.firstAccess)
    implementation(projects.feature.home)
    implementation(projects.feature.history)
    implementation(projects.feature.settings)
    implementation(projects.feature.firstAccess)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.navigation)
    implementation(libs.bundles.crashlytics)
    implementation(libs.androidx.core.splashscreen)
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.featureAndroidTest)
}