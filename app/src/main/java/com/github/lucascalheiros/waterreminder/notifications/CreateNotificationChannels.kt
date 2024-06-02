package com.github.lucascalheiros.waterreminder.notifications

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.content.ContextCompat
import com.github.lucascalheiros.waterreminder.R

fun Activity.createNotificationChannels() {
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val mChannel = NotificationChannel(
        NotificationChannelsInfo.remindNotificationChannelId,
        getString(R.string.remind_notification_channel_name),
        importance
    )
    mChannel.description = getString(R.string.remind_notification_channel_description)
    val notificationManager =
        ContextCompat.getSystemService(this, NotificationManager::class.java)
    notificationManager?.createNotificationChannel(mChannel)
}