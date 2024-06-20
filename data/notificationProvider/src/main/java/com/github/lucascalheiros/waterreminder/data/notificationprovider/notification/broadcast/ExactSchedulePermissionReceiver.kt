package com.github.lucascalheiros.waterreminder.data.notificationprovider.notification.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.github.lucascalheiros.waterreminder.common.util.DispatchersQualifier
import com.github.lucascalheiros.waterreminder.common.util.logDebug
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ExactSchedulePermissionReceiver : BroadcastReceiver(), KoinComponent {
    private val dispatcher: CoroutineDispatcher by inject(DispatchersQualifier.Io)

    override fun onReceive(context: Context, intent: Intent) {
        logDebug("Exact schedule permission has changed")
        if (intent.action == "android.app.action.SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED") {

        }
    }
}