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
    implementation(libs.kotlinx.dateTime)
    implementation(libs.koin.core)
}