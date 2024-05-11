package com.github.lucascalheiros.waterremindermvp

import android.app.Application
import com.github.lucascalheiros.waterremindermvp.di.appModule
import com.github.lucascalheiros.waterremindermvp.util.AndroidLoggingHandler
import com.github.lucascalheiros.waterremindermvp.util.KoinLogger
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