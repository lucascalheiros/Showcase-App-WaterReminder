plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.github.lucascalheiros.waterreminder.data.measuresystemprovider"
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(projects.domain.measureSystem)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.koin.core)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.androidTest)
}