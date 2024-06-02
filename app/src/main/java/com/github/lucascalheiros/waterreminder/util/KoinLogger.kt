package com.github.lucascalheiros.waterreminder.util

import com.github.lucascalheiros.waterreminder.common.util.LogLevel
import com.github.lucascalheiros.waterreminder.common.util.log
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE

class KoinLogger : Logger() {
    override fun display(level: Level, msg: MESSAGE) {
        log(level.toLogLevel(), msg)
    }
}

private fun Level.toLogLevel(): LogLevel {
    return when (this) {
        Level.DEBUG -> LogLevel.DEBUG
        Level.INFO -> LogLevel.INFO
        Level.WARNING -> LogLevel.WARNING
        Level.ERROR -> LogLevel.ERROR
        Level.NONE -> LogLevel.DEBUG
    }
}
