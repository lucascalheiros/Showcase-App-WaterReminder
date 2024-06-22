plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    toolchain {
        languageVersion = Configs.toolChainJavaLanguageVersion
    }
    sourceCompatibility = Configs.compileJavaVersion
    targetCompatibility = Configs.targetJavaVersion
}

dependencies {
    implementation(projects.common.util)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.koin.core)
}