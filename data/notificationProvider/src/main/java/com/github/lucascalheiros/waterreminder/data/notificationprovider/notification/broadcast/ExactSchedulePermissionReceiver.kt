package com.github.lucascalheiros.waterreminder.data.notificationprovider.notification.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.github.lucascalheiros.waterreminder.common.util.logDebug
import com.github.lucascalheiros.waterreminder.data.notificationprovider.notification.helpers.AlarmManagerNotificationSetupHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ExactSchedulePermissionReceiver : BroadcastReceiver(), KoinComponent {
    private val notificationSchedulerRepository: AlarmManagerNotificationSetupHelper by inject()

    override fun onReceive(context: Context, intent: Intent) {
        logDebug("broadcast: Exact schedule permission has changed")
        if (intent.action == "android.app.action.SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED") {
            notificationSchedulerRepository.setup()
        }
    }
}