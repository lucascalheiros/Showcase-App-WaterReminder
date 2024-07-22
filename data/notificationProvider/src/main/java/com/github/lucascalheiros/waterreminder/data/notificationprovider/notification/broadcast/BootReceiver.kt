package com.github.lucascalheiros.waterreminder.data.notificationprovider.notification.broadcast

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.github.lucascalheiros.waterreminder.common.util.logDebug
import com.github.lucascalheiros.waterreminder.data.notificationprovider.notification.helpers.AlarmManagerNotificationSetupHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Whenever user reboots the phone it is necessary to re-schedule all the alarms from alarm manager
 *  this behavior is necessary due to [AlarmManager] api limitation.
 */
class BootReceiver : BroadcastReceiver(), KoinComponent {
    private val notificationSchedulerRepository: AlarmManagerNotificationSetupHelper by inject()

    override fun onReceive(context: Context, intent: Intent) {
        logDebug("broadcast: Boot")
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            notificationSchedulerRepository.setup()
        }
    }
}