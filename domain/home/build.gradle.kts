plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = Configs.compileJavaVersion
    targetCompatibility = Configs.targetJavaVersion
}

dependencies {
    implementation(projects.domain.waterManagement)
    implementation(projects.domain.userInformation)
    implementation(libs.bundles.domain)
    testImplementation(libs.junit)
}