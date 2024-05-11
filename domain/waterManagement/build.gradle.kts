plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(projects.common.measureSystem)
    implementation(projects.common.util)
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.domain)

}