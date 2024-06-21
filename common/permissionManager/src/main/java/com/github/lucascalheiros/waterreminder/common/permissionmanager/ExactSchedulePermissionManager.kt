package com.github.lucascalheiros.waterreminder.common.permissionmanager

import android.app.AlarmManager
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.canScheduleExactAlarms(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val alarmManager = ContextCompat.getSystemService(this, AlarmManager::class.java)
        alarmManager?.canScheduleExactAlarms() == true
    } else {
        true
    }
}

@RequiresApi(Build.VERSION_CODES.S)
fun openExactSchedulePermissionSettingIntent(): Intent {
    return Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
}

@RequiresApi(Build.VERSION_CODES.S)
fun Context.showExactSchedulePermissionDialog(
    onSettings: () -> Unit,
    onCancel: () -> Unit,
): Dialog {
    return MaterialAlertDialogBuilder(this)
        .setTitle(R.string.alarm_permission_dialog_title)
        .setMessage(R.string.alarm_permission_dialog_message)
        .setPositiveButton(R.string.permission_dialog_confirm) { dialog, _ ->
            onSettings()
            dialog.dismiss()
        }
        .setNegativeButton(R.string.permission_dialog_cancel) { dialog, _ ->
            onCancel()
            dialog.dismiss()
        }
        .show()
}