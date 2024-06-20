package com.github.lucascalheiros.waterreminder.data.notificationprovider.notification.notifications

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import com.github.lucascalheiros.waterreminder.data.notificationprovider.R
import com.github.lucascalheiros.waterreminder.data.notificationprovider.notification.channels.NotificationChannels


fun Context.createRemindNotification(): Notification {
    val openAppIntent = packageManager.getLaunchIntentForPackage(packageName)
    val openAppPendingIntent: PendingIntent =
        PendingIntent.getActivity(this, 0, openAppIntent, PendingIntent.FLAG_IMMUTABLE)

    return NotificationCompat.Builder(this, NotificationChannels.WaterReminder.id)
        .setContentTitle(getString(R.string.notification_title))
        .setContentText(getString(R.string.notification_description))
        .setSmallIcon(com.github.lucascalheiros.waterreminder.common.ui.R.drawable.ic_water_drop_fill_color)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setCategory(NotificationCompat.CATEGORY_REMINDER)
        .setContentIntent(openAppPendingIntent)
        .apply {
//            shortcutWaterSources.take(3).forEach {
//                addAction(
//                    0,
//                    it.notificationActionFormat(this@createRemindNotification),
//                    null
//                )
//            }
        }
        .build()
}

//fun WaterSource.notificationActionFormat(context: Context): String {
//    return "%s %s".format(volume.shortValueAndUnitFormatted(context), waterSourceType.name)
//}