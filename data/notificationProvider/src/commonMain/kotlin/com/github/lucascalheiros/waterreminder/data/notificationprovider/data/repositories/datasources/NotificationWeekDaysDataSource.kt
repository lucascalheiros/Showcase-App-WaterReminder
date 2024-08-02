package com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDay
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDayNotificationState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class NotificationWeekDaysDataSource(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun weekDaysEnabled(dayTime: DayTime): List<WeekDayNotificationState> {
        return (dataStore.data.first()[dayTime.weekDaysKey()] ?: initialData).mapNotNull { it.toIntOrNull() }.let { list ->
            WeekDay.entries.map { WeekDayNotificationState(it, list.contains(it.dayNumber)) }
        }
    }

    fun weekDaysEnabledFlow(dayTime: DayTime): Flow<List<WeekDayNotificationState>> {
        return dataStore.data.map { preferences ->
            (preferences[dayTime.weekDaysKey()] ?: initialData).mapNotNull { it.toIntOrNull() }
        }.map { list ->
            WeekDay.entries.map { WeekDayNotificationState(it, list.contains(it.dayNumber)) }
        }
    }

    suspend fun setState(dayTime: DayTime, weekDaysState: List<WeekDayNotificationState>) {
        dataStore.edit { preferences ->
            preferences[dayTime.weekDaysKey()] =
                weekDaysState.filter { it.isEnabled }.map { it.weekDay.dayNumber.toString() }.toSet()
        }
    }

    companion object {
        private fun DayTime.weekDaysKey() = stringSetPreferencesKey("weekDaysKey:${dayMinutes}")
        private val initialData = listOf(0, 1, 2, 3, 4, 5, 6).map { it.toString() }.toSet()
    }
}
