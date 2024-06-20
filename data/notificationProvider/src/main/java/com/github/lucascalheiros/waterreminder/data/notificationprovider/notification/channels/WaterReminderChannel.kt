package com.github.lucascalheiros.waterreminder.data.notificationprovider.notification.channels

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentResolver
import android.net.Uri
import androidx.core.content.ContextCompat
import com.github.lucascalheiros.waterreminder.data.notificationprovider.R

fun Activity.createWaterReminderChannel() {
    val soundUri: Uri = Uri.Builder()
        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(packageName)
        .appendPath("${R.raw.water_pouring}")
        .build()


    val channel = NotificationChannel(
        NotificationChannels.WaterReminder.id,
        getString(R.string.remind_notification_channel_name),
        NotificationManager.IMPORTANCE_DEFAULT
    )
    channel.description = getString(R.string.remind_notification_channel_description)
    channel.setSound(soundUri, null)
    val notificationManager =
        ContextCompat.getSystemService(this, NotificationManager::class.java)
    notificationManager?.createNotificationChannel(channel)
}