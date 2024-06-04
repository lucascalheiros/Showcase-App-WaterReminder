package com.github.lucascalheiros.waterreminder.notifications

import android.app.Notification
import android.content.Context
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.github.lucascalheiros.waterreminder.R


fun Context.createRemindNotification(): Notification {
    val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

    return NotificationCompat.Builder(this, NotificationChannelsInfo.remindNotificationChannelId)
        .setContentTitle(getString(R.string.notification_title))
        .setContentText(getString(R.string.notification_description))
        .setSmallIcon(R.drawable.ic_notifications_black_24dp)
        .setSound(alarmSound)
        .build()
}