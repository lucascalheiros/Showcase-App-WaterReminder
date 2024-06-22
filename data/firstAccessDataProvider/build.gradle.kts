plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider"
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
}

dependencies {
    implementation(projects.domain.firstAccess)
    implementation(libs.androidx.core.ktx)
    implementation(libs.koin.core)
    implementation(libs.kotlinx.dateTime)
    implementation(libs.androidx.datastore)
}