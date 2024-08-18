import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.sqlDelight)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.koin.android)
            implementation(libs.androidx.core.ktx)
            implementation(projects.common.ui)
            implementation(projects.common.permissionManager)
            implementation(libs.sqlDelight.android)
        }
        commonMain.dependencies {
            implementation(projects.common.util)
            implementation(projects.domain.remindNotifications)
            implementation(libs.koin.core)
            implementation(libs.androidx.datastore)
            implementation(libs.sqlDelight.coroutines)
        }
        iosMain.dependencies {
            implementation(libs.sqlDelight.native)
        }
    }
}

android {
    namespace = "com.github.lucascalheiros.waterreminder.data.notificationprovider"
    compileSdk = Configs.COMPILE_SDK
    compileOptions {
        sourceCompatibility = Configs.compileJavaVersion
        targetCompatibility = Configs.targetJavaVersion
    }
    defaultConfig {
        minSdk = Configs.MIN_SDK
    }
}

sqldelight {
    databases {
        create("ReminderNotificationDatabase") {
            packageName.set("com.github.lucascalheiros.waterreminder.data.notificationprovider")
        }
    }
}
