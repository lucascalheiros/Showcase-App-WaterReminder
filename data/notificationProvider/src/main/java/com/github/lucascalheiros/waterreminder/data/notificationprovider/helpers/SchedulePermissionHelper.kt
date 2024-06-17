package com.github.lucascalheiros.waterreminder.data.notificationprovider.helpers

import android.app.AlarmManager
import android.content.Context
import android.os.Build
import androidx.core.content.ContextCompat

fun Context.canScheduleExactAlarms(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val alarmManager = ContextCompat.getSystemService(this, AlarmManager::class.java)
        alarmManager?.canScheduleExactAlarms() == true
    } else {
        true
    }
}
