package com.github.lucascalheiros.waterreminder.feature.home.test

import android.util.Log
import java.util.logging.Handler
import java.util.logging.Level
import java.util.logging.LogManager
import java.util.logging.LogRecord

class TestLoggingHandler : Handler() {

    override fun close() {
    }

    override fun flush() {
    }

    override fun publish(record: LogRecord) {
        val tag = record.loggerName
        val level = record.level.toAndroidLevel()
        val message = record.thrown?.let { thrown ->
            "${record.message}: ${Log.getStackTraceString(thrown)}"
        } ?: record.message

        println("[$level] [$tag] $message")
    }

    private fun Level.toAndroidLevel(): Int =
        when (intValue()) {
            Level.SEVERE.intValue() -> Log.ERROR
            Level.WARNING.intValue() -> Log.WARN
            Level.INFO.intValue() -> Log.INFO
            Level.FINE.intValue() -> Log.DEBUG
            Level.FINER.intValue() -> Log.VERBOSE
            else -> Log.DEBUG
        }

    companion object {
        fun setup() {
            val rootLogger = LogManager.getLogManager().getLogger("")
            for (handler in rootLogger.handlers) {
                rootLogger.removeHandler(handler)
            }
            rootLogger.addHandler(TestLoggingHandler())
            rootLogger.level = Level.FINER
        }
    }
}