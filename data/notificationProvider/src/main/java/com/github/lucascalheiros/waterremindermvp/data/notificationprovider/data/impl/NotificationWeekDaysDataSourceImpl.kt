package com.github.lucascalheiros.waterremindermvp.data.notificationprovider.data.impl

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.github.lucascalheiros.waterremindermvp.data.notificationprovider.data.NotificationWeekDaysDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class NotificationWeekDaysDataSourceImpl(
    private val context: Context
) : NotificationWeekDaysDataSource {

    override suspend fun weekDaysEnabled(): List<Int> {
        return context.dataStore.data.first()[weekDaysKey].orEmpty().mapNotNull { it.toIntOrNull() }
    }

    override fun weekDaysEnabledFlow(): Flow<List<Int>> {
        return context.dataStore.data.map { preferences ->
            preferences[weekDaysKey].orEmpty().mapNotNull { it.toIntOrNull() }
        }
    }

    override suspend fun removeWeekDay(weekDayValue: Int) {
        context.dataStore.edit { preferences ->
            preferences[weekDaysKey] =
                weekDaysEnabled().filter { it != weekDayValue }.map { it.toString() }.toSet()
        }
    }

    override suspend fun addWeekDay(weekDayValue: Int) {
        context.dataStore.edit { preferences ->
            preferences[weekDaysKey] =
                (weekDaysEnabled() + listOf(weekDayValue)).map { it.toString() }.toSet()
        }
    }

    companion object {
        private val weekDaysKey = stringSetPreferencesKey("weekDaysKey")
    }
}
