plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = Configs.compileJavaVersion
    targetCompatibility = Configs.targetJavaVersion
}