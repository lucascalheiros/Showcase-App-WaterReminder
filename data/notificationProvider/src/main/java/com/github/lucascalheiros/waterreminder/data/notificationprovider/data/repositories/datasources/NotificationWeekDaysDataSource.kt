package com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class NotificationWeekDaysDataSource(
    private val dataStore: DataStore<Preferences>
) {

    private fun MutablePreferences.initializeData() {
        if (!contains(weekDaysKey)) {
            set(weekDaysKey, initialData)
        }
    }

    suspend fun weekDaysEnabled(): List<Int> {
        return (dataStore.data.first()[weekDaysKey] ?: initialData).mapNotNull { it.toIntOrNull() }
    }

    fun weekDaysEnabledFlow(): Flow<List<Int>> {
        return dataStore.data.map { preferences ->
            (preferences[weekDaysKey] ?: initialData).mapNotNull { it.toIntOrNull() }
        }
    }

    suspend fun removeWeekDay(weekDayValue: Int) {
        dataStore.edit { preferences ->
            preferences.initializeData()
            preferences[weekDaysKey] =
                weekDaysEnabled().filter { it != weekDayValue }.map { it.toString() }.toSet()
        }
    }

    suspend fun addWeekDay(weekDayValue: Int) {
        dataStore.edit { preferences ->
            preferences.initializeData()
            preferences[weekDaysKey] =
                (weekDaysEnabled() + listOf(weekDayValue)).map { it.toString() }.toSet()
        }
    }

    companion object {
        private val weekDaysKey = stringSetPreferencesKey("weekDaysKey")
        private val initialData = listOf(0, 1, 2, 3, 4, 5, 6).map { it.toString() }.toSet()
    }
}
