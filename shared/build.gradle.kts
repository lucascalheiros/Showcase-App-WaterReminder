import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.cocoapods)
    alias(libs.plugins.nativeCoroutines)
    alias(libs.plugins.kotlin.ksp)
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

    cocoapods {
        version = "1.0"
        summary = "Some description for a Kotlin/Native module"
        homepage = "Link to a Kotlin/Native module homepage"
        podfile = project.file("../iosApp/Podfile")

        framework {
            baseName = "Shared"
            isStatic = false
            binaryOption("bundleId", "com.github.lucascalheiros.waterreminder.shared")
            export(projects.domain.measureSystem)
            export(projects.domain.userInformation)
            export(projects.domain.waterManagement)
            export(projects.domain.remindNotifications)
            export(projects.domain.home)
            export(projects.domain.history)
            export(projects.domain.firstAccess)
            export(projects.data.measureSystemProvider)
            export(projects.data.userProfileProvider)
            export(projects.data.waterDataProvider)
            export(projects.data.notificationProvider)
            export(projects.data.home)
            export(projects.data.firstAccessDataProvider)
            export(projects.data.themeProvider)
            export(projects.common.util)
            linkerOpts = mutableListOf("-lsqlite3")
        }

        // Maps custom Xcode configuration to NativeBuildType
        xcodeConfigurationToNativeBuildType["CUSTOM_DEBUG"] = NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] = NativeBuildType.RELEASE
    }

    
    sourceSets {
        all {
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }
        commonMain.dependencies {
            api(projects.domain.measureSystem)
            api(projects.domain.userInformation)
            api(projects.domain.waterManagement)
            api(projects.domain.remindNotifications)
            api(projects.domain.home)
            api(projects.domain.history)
            api(projects.domain.firstAccess)
            api(projects.data.measureSystemProvider)
            api(projects.data.userProfileProvider)
            api(projects.data.waterDataProvider)
            api(projects.data.notificationProvider)
            api(projects.data.home)
            api(projects.data.firstAccessDataProvider)
            api(projects.data.themeProvider)
            api(projects.common.util)
            implementation(libs.kotlinx.coroutines)
            implementation(libs.koin.core)
        }
    }
}

android {
    namespace = "com.github.lucascalheiros.waterreminder.shared"
    compileSdk = Configs.COMPILE_SDK
    compileOptions {
        sourceCompatibility = Configs.compileJavaVersion
        targetCompatibility = Configs.targetJavaVersion
    }
    defaultConfig {
        minSdk = Configs.MIN_SDK
    }
}
