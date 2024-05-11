package com.github.lucascalheiros.waterremindermvp

import android.app.Application
import com.github.lucascalheiros.waterremindermvp.util.AndroidLoggingHandler

class WaterReminderApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        AndroidLoggingHandler.setup()
    }
}