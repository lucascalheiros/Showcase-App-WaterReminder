package com.github.lucascalheiros.waterreminder.common.permissionmanager

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder


fun Context.openNotificationPermissionSettingIntent(): Intent {
    return Intent().apply {
        action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
        putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
    }
}

fun Context.showNotificationPermissionDialog(
    onSettings: () -> Unit,
    onCancel: () -> Unit,
): Dialog {
    return MaterialAlertDialogBuilder(this)
        .setTitle(R.string.notification_permission_dialog_title)
        .setMessage(R.string.notification_permission_dialog_message)
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

fun Context.hasNotificationPermission(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Activity.shouldShowPermissionRationale(): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(
        this,
        Manifest.permission.POST_NOTIFICATIONS
    )
}

/**
 * When the rationally is not needed and the permission was denied then ask for manual permission change
 */
fun Activity.shouldAskNotificationPermissionManually(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        !hasNotificationPermission() && !shouldShowPermissionRationale()
    } else {
        false
    }
}
