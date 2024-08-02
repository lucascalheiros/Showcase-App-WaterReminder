import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "DomainFirstAccess"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.domain.waterManagement)
            implementation(projects.domain.remindNotifications)
            implementation(projects.domain.measureSystem)
            implementation(projects.domain.userInformation)
            implementation(libs.bundles.domain)
//            testImplementation(libs.junit)
        }
    }
}

android {
    namespace = "com.github.lucascalheiros.waterreminder.domain.firstaccess"
    compileSdk = Configs.COMPILE_SDK
    compileOptions {
        sourceCompatibility = Configs.compileJavaVersion
        targetCompatibility = Configs.targetJavaVersion
    }
    defaultConfig {
        minSdk = Configs.MIN_SDK
    }
}
