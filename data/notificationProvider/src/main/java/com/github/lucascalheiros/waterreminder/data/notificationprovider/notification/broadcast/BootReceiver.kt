package com.github.lucascalheiros.waterreminder.data.notificationprovider.notification.broadcast

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.github.lucascalheiros.waterreminder.common.util.DispatchersQualifier
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Whenever user reboots the phone it is necessary to re-schedule all the alarms from alarm manager
 *  this behavior is necessary due to [AlarmManager] api limitation.
 */
class BootReceiver : BroadcastReceiver(), KoinComponent {
    private val dispatcher: CoroutineDispatcher by inject(DispatchersQualifier.Io)

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {

        }
    }
}