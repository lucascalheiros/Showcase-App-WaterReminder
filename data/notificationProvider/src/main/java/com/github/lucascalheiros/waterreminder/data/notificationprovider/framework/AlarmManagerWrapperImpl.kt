package com.github.lucascalheiros.waterreminder.data.notificationprovider.framework

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.github.lucascalheiros.waterreminder.common.util.logError
import com.github.lucascalheiros.waterreminder.common.util.logVerbose
import com.github.lucascalheiros.waterreminder.common.permissionmanager.canScheduleExactAlarms
import java.util.Calendar

internal class AlarmManagerWrapperImpl(
    private val context: Context
): AlarmManagerWrapper {
    private fun getNotificationIntent(dayTimeInMinutes: Int): Intent {
        val intent = Intent(INTENT_ACTION).apply {
            putExtra(MINUTES_OF_DAY_DATA_EXTRA, dayTimeInMinutes)
        }
        val packageManager = context.packageManager
        val supportedBroadcastInfo = packageManager.queryBroadcastReceivers(intent, 0).firstOrNull()
            ?: throw BroadcastWithIntentFilterWasNotFound()
        val componentName = ComponentName(
            supportedBroadcastInfo.activityInfo.packageName,
            supportedBroadcastInfo.activityInfo.name
        )
        intent.setComponent(componentName)
        return intent
    }

    override fun createAlarmSchedule(dayTimeInMinutes: Int) {
        if (!context.canScheduleExactAlarms()) {
            logError("Exact schedule permission was not set, unable to proceed with alarm creation")
            return
        }
        logVerbose("Creating alarm for $dayTimeInMinutes")
        val alarmManager = ContextCompat.getSystemService(context, AlarmManager::class.java)

        val alarmIntent =
            PendingIntent.getBroadcast(
                context,
                dayTimeInMinutes,
                getNotificationIntent(dayTimeInMinutes),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, dayTimeInMinutes / 60)
            set(Calendar.MINUTE, dayTimeInMinutes % 60)
            set(Calendar.SECOND, 0)
        }

        val timeInMillis = calendar.timeInMillis.takeIf {
            it > System.currentTimeMillis()
        } ?: calendar.apply {
            add(Calendar.DATE, 1)
        }.timeInMillis

        alarmManager?.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            alarmIntent
        )
    }

    override fun cancelAlarmSchedule(dayTimeInMinutes: Int) {
        if (!context.canScheduleExactAlarms()) {
            logError("Exact schedule permission was not set, unable to proceed with alarm cancellation")
            return
        }
        val alarmManager = ContextCompat.getSystemService(context, AlarmManager::class.java)
        val alarmIntent =
            PendingIntent.getBroadcast(
                context,
                dayTimeInMinutes,
                getNotificationIntent(dayTimeInMinutes),
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_NO_CREATE
            ) ?: return
        alarmManager?.cancel(alarmIntent)
    }

    class BroadcastWithIntentFilterWasNotFound :
        Exception("Make sure intent filter with action $INTENT_ACTION is added to AndroidManifest")

    companion object {
        private const val INTENT_ACTION =
            "com.github.lucascalheiros.waterreminder.data.notificationprovider.RemindNotification"

        const val MINUTES_OF_DAY_DATA_EXTRA = "MINUTES_OF_DAY_DATA_EXTRA"
    }
}