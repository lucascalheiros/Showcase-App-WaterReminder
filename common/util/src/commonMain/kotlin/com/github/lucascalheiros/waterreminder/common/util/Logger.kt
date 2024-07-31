package com.github.lucascalheiros.waterreminder.common.util

import java.util.logging.Level
import java.util.logging.Logger

private fun LogLevel.toLevel(): Level {
    return when (this) {
        LogLevel.VERBOSE -> Level.FINER
        LogLevel.DEBUG -> Level.FINE
        LogLevel.INFO -> Level.INFO
        LogLevel.WARNING -> Level.WARNING
        LogLevel.ERROR -> Level.SEVERE
    }
}

private val Any.logger: Logger
    get() = Logger.getLogger(this::class.simpleName)

fun Any.log(logLevel: LogLevel, message: String, throwable: Throwable? = null) {
    logger.log(logLevel.toLevel(), message, throwable)
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