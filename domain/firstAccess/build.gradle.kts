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
    implementation(projects.domain.waterManagement)
    implementation(projects.domain.remindNotifications)
    implementation(projects.domain.measureSystem)
    implementation(projects.domain.userInformation)
    implementation(libs.bundles.domain)
    testImplementation(libs.junit)
}