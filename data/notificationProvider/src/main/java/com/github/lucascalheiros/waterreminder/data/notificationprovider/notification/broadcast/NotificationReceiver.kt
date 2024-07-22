package com.github.lucascalheiros.waterreminder.data.notificationprovider.notification.broadcast

import android.Manifest
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import com.github.lucascalheiros.waterreminder.common.util.logDebug
import com.github.lucascalheiros.waterreminder.common.util.logError
import com.github.lucascalheiros.waterreminder.data.notificationprovider.notification.channels.createWaterReminderChannel
import com.github.lucascalheiros.waterreminder.data.notificationprovider.notification.helpers.PreventNotificationByWeekDayHelper
import com.github.lucascalheiros.waterreminder.data.notificationprovider.notification.notifications.createRemindNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NotificationReceiver : BroadcastReceiver(), KoinComponent {

    private val preventNotificationByWeekDayHelper: PreventNotificationByWeekDayHelper by inject()

    override fun onReceive(context: Context, intent: Intent) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            logError("::onReceive Notification permission not granted, notification will be ignored")
            return
        }
        logDebug("::onReceive Notification received")
        context.createWaterReminderChannel()
        CoroutineScope(Dispatchers.IO).launch {
            if (preventNotificationByWeekDayHelper.shouldPreventNotificationFromIntent(intent)) {
                return@launch
            }
            val notificationManager = getSystemService(context, NotificationManager::class.java)
            val notification = context.createRemindNotification()
            notificationManager?.notify(0, notification)
        }
    }

}