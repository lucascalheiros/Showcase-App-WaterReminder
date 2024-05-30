package com.github.lucascalheiros.waterremindermvp.data.notificationprovider.framework.impl

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.getSystemService
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.github.lucascalheiros.waterremindermvp.data.notificationprovider.framework.NotificationProviderWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import java.util.Calendar

internal class NotificationProviderWrapperImpl(
    private val context: Context
) : NotificationProviderWrapper, KoinComponent {

    private val Context.dataStore by preferencesDataStore(name = NOTIFICATION_PREFERENCES)

    override suspend fun setup() {
        allRemindNotifications().forEach {
            createAlarmSchedule(it)
        }
    }

    override suspend fun scheduleRemindNotification(dayTimeInMinutes: Int) {
        addToStorage(dayTimeInMinutes)
        createAlarmSchedule(dayTimeInMinutes)
    }

    override suspend fun cancelRemindNotification(dayTimeInMinutes: Int) {
        cancelAlarmSchedule(dayTimeInMinutes)
        removeFromStorage(dayTimeInMinutes)
    }

    override suspend fun allRemindNotifications(): List<Int> {
        return context.dataStore.data.first()[scheduledReminderDayMinuteEpochs].orEmpty()
            .map { it.toInt() }
    }

    override fun allRemindNotificationsFlow(): Flow<List<Int>> {
        return context.dataStore.data.map { preferences ->
            preferences[scheduledReminderDayMinuteEpochs].orEmpty()
                .map { it.toInt() }
        }
    }

    private fun getNotificationIntent(): Intent {
        val intent = Intent(INTENT_ACTION)
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

    private fun createAlarmSchedule(dayTimeInMinutes: Int) {
        val alarmManager = getSystemService(context, AlarmManager::class.java)

        val alarmIntent =
            PendingIntent.getBroadcast(
                context,
                dayTimeInMinutes,
                getNotificationIntent(),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

        val timeInMillis = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, dayTimeInMinutes / 60)
            set(Calendar.MINUTE, dayTimeInMinutes % 60)
            set(Calendar.SECOND, 0)
        }.timeInMillis
        alarmManager?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmIntent
        )
    }

    private fun cancelAlarmSchedule(dayTimeInMinutes: Int) {
        val alarmManager = getSystemService(context, AlarmManager::class.java)
        val alarmIntent =
            PendingIntent.getBroadcast(
                context,
                dayTimeInMinutes,
                getNotificationIntent(),
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_NO_CREATE
            ) ?: return
        alarmManager?.cancel(alarmIntent)
    }

    private suspend fun addToStorage(dayTimeInMinutes: Int) {
        with(context.dataStore.data.first()) {
            val data = get(scheduledReminderDayMinuteEpochs).orEmpty().toMutableSet().apply {
                add(dayTimeInMinutes.toString())
            }
            context.dataStore.edit {
                it[scheduledReminderDayMinuteEpochs] = data
            }
        }
    }

    private suspend fun removeFromStorage(dayTimeInMinutes: Int) {
        with(context.dataStore.data.first()) {
            val data = get(scheduledReminderDayMinuteEpochs).orEmpty().toMutableSet().apply {
                remove(dayTimeInMinutes.toString())
            }
            context.dataStore.edit {
                it[scheduledReminderDayMinuteEpochs] = data
            }
        }
    }

    class BroadcastWithIntentFilterWasNotFound : Exception("Make sure intent filter with action $INTENT_ACTION is added to AndroidManifest")

    companion object {
        private const val INTENT_ACTION = "com.github.lucascalheiros.waterremindermvp.data.notificationprovider.RemindNotification"
        private const val NOTIFICATION_PREFERENCES = "com.github.lucascalheiros.waterremindermvp.data.notificationprovider.datastore"
        private val scheduledReminderDayMinuteEpochs =
            stringSetPreferencesKey("scheduledReminderDayMinuteEpochs")
    }
}
