package com.github.lucascalheiros.waterremindermvp.notifications.broadcast

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import androidx.core.content.ContextCompat.getSystemService
import com.github.lucascalheiros.waterremindermvp.notifications.createRemindNotification

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = getSystemService(context, NotificationManager::class.java)
        val notification = context.createRemindNotification()
        notificationManager?.notify((SystemClock.currentThreadTimeMillis() / 1000L).toInt(), notification)
    }
}