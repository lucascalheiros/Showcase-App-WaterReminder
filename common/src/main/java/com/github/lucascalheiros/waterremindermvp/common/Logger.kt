package com.github.lucascalheiros.waterremindermvp.common

import java.util.logging.Level
import java.util.logging.Logger

private val Any.logger: Logger
    get() = Logger.getLogger(this::class.simpleName)

fun Any.logVerbose(message: String, throwable: Throwable? = null) {
    logger.log(Level.FINER, message, throwable)
}

fun Any.logDebug(message: String, throwable: Throwable? = null) {
    logger.log(Level.FINE, message, throwable)
}

fun Any.logInfo(message: String, throwable: Throwable? = null) {
    logger.log(Level.INFO, message, throwable)
}

fun Any.logWarn(message: String, throwable: Throwable? = null) {
    logger.log(Level.WARNING, message, throwable)
}
fun Any.logError(message: String, throwable: Throwable? = null) {
    logger.log(Level.SEVERE, message, throwable)
}