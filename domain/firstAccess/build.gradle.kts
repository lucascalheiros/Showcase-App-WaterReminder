plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(projects.domain.waterManagement)
    implementation(projects.domain.remindNotifications)
    implementation(projects.domain.measureSystem)
    implementation(projects.domain.userInformation)
    implementation(libs.bundles.domain)
    testImplementation(libs.junit)
}