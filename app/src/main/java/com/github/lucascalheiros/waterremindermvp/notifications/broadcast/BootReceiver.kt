package com.github.lucascalheiros.waterremindermvp.notifications.broadcast

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.github.lucascalheiros.waterremindermvp.common.util.DispatchersQualifier
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.SetupRemindNotificationsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Whenever user reboots the phone it is necessary to re-schedule all the alarms from alarm manager
 *  this behavior is necessary due to [AlarmManager] api limitation.
 */
class BootReceiver : BroadcastReceiver(), KoinComponent {
    private val setupRemindNotificationsUseCase: SetupRemindNotificationsUseCase by inject()
    private val dispatcher: CoroutineDispatcher by inject(DispatchersQualifier.Io)

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            CoroutineScope(dispatcher).launch {
                setupRemindNotificationsUseCase()
            }
        }
    }
}