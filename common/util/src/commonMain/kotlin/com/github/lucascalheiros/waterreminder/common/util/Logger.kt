package com.github.lucascalheiros.waterreminder.common.util

import io.github.aakira.napier.Napier

expect fun setupLogs()

fun Any.log(logLevel: LogLevel, message: String, throwable: Throwable? = null) {
    val tag = this::class.simpleName
    return when (logLevel) {
        LogLevel.VERBOSE -> Napier.v(message, throwable, tag)
        LogLevel.DEBUG -> Napier.d(message, throwable, tag)
        LogLevel.INFO -> Napier.i(message, throwable, tag)
        LogLevel.WARNING -> Napier.w(message, throwable, tag)
        LogLevel.ERROR -> Napier.e(message, throwable, tag)
    }
}

fun Any.logVerbose(message: String, throwable: Throwable? = null) {
    log(LogLevel.VERBOSE, message, throwable)
}

fun Any.logDebug(message: String, throwable: Throwable? = null) {
    log(LogLevel.DEBUG, message, throwable)
}

fun Any.logInfo(message: String, throwable: Throwable? = null) {
    log(LogLevel.INFO, message, throwable)
}

fun Any.logWarn(message: String, throwable: Throwable? = null) {
    log(LogLevel.WARNING, message, throwable)
}

fun Any.logError(message: String, throwable: Throwable? = null) {
    log(LogLevel.ERROR, message, throwable)
}