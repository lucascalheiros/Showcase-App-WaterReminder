import org.gradle.api.JavaVersion
import org.gradle.jvm.toolchain.JavaLanguageVersion

object Configs {
    const val COMPILE_SDK = 35
    const val TARGET_SDK = 35
    const val MIN_SDK = 26
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"
    const val APPLICATION_ID = "com.github.lucascalheiros.waterreminder"
    const val JVM_TARGET = "17"
    val toolChainJavaLanguageVersion: JavaLanguageVersion = JavaLanguageVersion.of(17)
    val compileJavaVersion = JavaVersion.VERSION_17
    val targetJavaVersion = JavaVersion.VERSION_17
}