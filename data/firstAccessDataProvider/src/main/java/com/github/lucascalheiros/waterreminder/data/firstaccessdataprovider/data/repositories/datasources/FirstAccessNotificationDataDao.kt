package com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.data.repositories.datasources

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.data.models.FirstAccessNotificationDataDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalTime


class FirstAccessNotificationDataDao(
    private val dataStore: DataStore<Preferences>
) {
    fun firstAccessNotificationFlow(): Flow<FirstAccessNotificationDataDb> {
        return dataStore.data.map { preferences ->
            FirstAccessNotificationDataDb(
                preferences[notificationStartKey]?.let {
                    LocalTime.fromSecondOfDay(it)
                } ?: LocalTime(8, 0),
                preferences[notificationStopKey]?.let {
                    LocalTime.fromSecondOfDay(it)
                } ?: LocalTime(20, 0),
                preferences[notificationFrequencyKey]?.let {
                    LocalTime.fromSecondOfDay(it)
                } ?: LocalTime(2, 0),
                preferences[notificationsStateKey] ?: true
            )
        }
    }

    suspend fun setNotificationStartTime(time: LocalTime) {
        dataStore.edit {
            it[notificationStartKey] = time.toSecondOfDay()
        }
    }

    suspend fun setNotificationStopTime(time: LocalTime) {
        dataStore.edit {
            it[notificationStopKey] = time.toSecondOfDay()
        }
    }

    suspend fun setNotificationPeriod(time: LocalTime) {
        dataStore.edit {
            it[notificationFrequencyKey] = time.toSecondOfDay()
        }
    }

    suspend fun setNotificationState(state: Boolean) {
        dataStore.edit {
            it[notificationsStateKey] = state
        }
    }

    companion object {
        private val notificationStartKey = intPreferencesKey("notificationStartKey")
        private val notificationStopKey = intPreferencesKey("notificationStopKey")
        private val notificationFrequencyKey = intPreferencesKey("notificationFrequencyKey")
        private val notificationsStateKey = booleanPreferencesKey("notificationsStateKey")
    }
}