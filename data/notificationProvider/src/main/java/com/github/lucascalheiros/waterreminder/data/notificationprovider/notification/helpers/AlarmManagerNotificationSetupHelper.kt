package com.github.lucascalheiros.waterreminder.data.notificationprovider.notification.helpers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.github.lucascalheiros.waterreminder.common.util.logError
import com.github.lucascalheiros.waterreminder.data.notificationprovider.framework.AlarmManagerWrapper
import com.github.lucascalheiros.waterreminder.data.notificationprovider.notification.channels.createWaterReminderChannel
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories.NotificationSchedulerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class AlarmManagerNotificationSetupHelper(
    private val dispatcher: CoroutineDispatcher,
    private val alarmManagerWrapper: AlarmManagerWrapper,
    private val notificationSchedulerRepository: NotificationSchedulerRepository,
    private val context: Context,
) {
    fun setup() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            logError("::onReceive Notification permission not granted, notification will be ignored")
            return
        }
        CoroutineScope(dispatcher).launch {
            context.createWaterReminderChannel()
            notificationSchedulerRepository.allRemindNotifications().forEach {
                alarmManagerWrapper.createAlarmSchedule(it.dayMinutes)
            }
        }
    }
}