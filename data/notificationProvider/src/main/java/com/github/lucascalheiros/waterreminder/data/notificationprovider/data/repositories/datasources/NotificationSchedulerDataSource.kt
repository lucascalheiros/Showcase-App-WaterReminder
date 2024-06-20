package com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class NotificationSchedulerDataSource(
    private val dataStore: DataStore<Preferences>,
    private val alarmManagerWrapper: AlarmManagerWrapper
) {

    suspend fun setup() {
        allRemindNotifications().forEach {
            alarmManagerWrapper.createAlarmSchedule(it)
        }
    }

    suspend fun scheduleRemindNotification(dayTimeInMinutes: Int) {
        addToStorage(dayTimeInMinutes)
        alarmManagerWrapper.createAlarmSchedule(dayTimeInMinutes)
    }

    suspend fun cancelRemindNotification(dayTimeInMinutes: Int) {
        alarmManagerWrapper.cancelAlarmSchedule(dayTimeInMinutes)
        removeFromStorage(dayTimeInMinutes)
    }

    suspend fun allRemindNotifications(): List<Int> {
        return dataStore.data.first()[scheduledReminderDayMinuteEpochs].orEmpty()
            .map { it.toInt() }.sorted()
    }

    fun allRemindNotificationsFlow(): Flow<List<Int>> {
        return dataStore.data.map { preferences ->
            preferences[scheduledReminderDayMinuteEpochs].orEmpty()
                .map { it.toInt() }.sorted()
        }
    }

    private suspend fun addToStorage(dayTimeInMinutes: Int) {
        with(dataStore.data.first()) {
            val data = get(scheduledReminderDayMinuteEpochs).orEmpty().toMutableSet().apply {
                add(dayTimeInMinutes.toString())
            }
            dataStore.edit {
                it[scheduledReminderDayMinuteEpochs] = data
            }
        }
    }

    private suspend fun removeFromStorage(dayTimeInMinutes: Int) {
        with(dataStore.data.first()) {
            val data = get(scheduledReminderDayMinuteEpochs).orEmpty().toMutableSet().apply {
                remove(dayTimeInMinutes.toString())
            }
            dataStore.edit {
                it[scheduledReminderDayMinuteEpochs] = data
            }
        }
    }

    companion object {
        private val scheduledReminderDayMinuteEpochs =
            stringSetPreferencesKey("scheduledReminderDayMinuteEpochs")
    }
}
