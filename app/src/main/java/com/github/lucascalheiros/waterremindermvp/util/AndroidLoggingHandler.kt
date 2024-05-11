// Credits to Dmitry Akishin
// https://proandroiddev.com/logging-in-a-multi-module-android-project-7294382e59fa
package com.github.lucascalheiros.waterremindermvp.util

import android.util.Log
import com.github.lucascalheiros.waterremindermvp.BuildConfig
import java.util.logging.Handler
import java.util.logging.Level
import java.util.logging.LogManager
import java.util.logging.LogRecord

class AndroidLoggingHandler : Handler() {

    override fun isLoggable(record: LogRecord?): Boolean =
        super.isLoggable(record) && BuildConfig.DEBUG

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

        try {
            Log.println(level, tag, message)
        } catch (e: RuntimeException) {
            Log.e(this.javaClass.simpleName, "Error logging message", e)
        }
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
            rootLogger.addHandler(AndroidLoggingHandler())
            rootLogger.level = Level.FINER
        }
    }
}