import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

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
        podfile = project.file("../../iosApp/Podfile")

        framework {
            baseName = "DomainHome"
            isStatic = false
            @OptIn(ExperimentalKotlinGradlePluginApi::class)
            transitiveExport = false
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.domain.waterManagement)
            implementation(projects.domain.userInformation)
            implementation(libs.bundles.domain)
//            testImplementation(libs.junit)
        }
    }
}

android {
    namespace = "com.github.lucascalheiros.waterreminder.domain.home"
    compileSdk = Configs.COMPILE_SDK
    compileOptions {
        sourceCompatibility = Configs.compileJavaVersion
        targetCompatibility = Configs.targetJavaVersion
    }
    defaultConfig {
        minSdk = Configs.MIN_SDK
    }
}
