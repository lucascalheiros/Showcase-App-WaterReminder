package com.github.lucascalheiros.waterreminder

import android.app.Application
import com.github.lucascalheiros.waterreminder.di.appModule
import com.github.lucascalheiros.waterreminder.util.AndroidLoggingHandler
import com.github.lucascalheiros.waterreminder.util.KoinLogger
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class WaterReminderApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        AndroidLoggingHandler.setup()

        startKoin {
            logger(KoinLogger())
            androidContext(this@WaterReminderApplication)
            modules(appModule)
        }
    }
}