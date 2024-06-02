package com.github.lucascalheiros.waterreminder.notifications

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import com.github.lucascalheiros.waterreminder.R

fun Context.createRemindNotification(): Notification {
    return NotificationCompat.Builder(this, NotificationChannelsInfo.remindNotificationChannelId)
        .setContentTitle(getString(R.string.notification_title))
        .setContentText(getString(R.string.notification_description))
        .setSmallIcon(R.drawable.ic_notifications_black_24dp)
        .build()
}