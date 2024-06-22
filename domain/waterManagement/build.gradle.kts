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
    implementation(projects.domain.measureSystem)
    implementation(projects.common.util)
    implementation(libs.bundles.domain)
}