package com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class NotificationEnabledDataSource(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun isNotificationEnabled(): Boolean {
        return dataStore.data.first()[notificationEnabledKey] ?: true
    }

    fun isNotificationEnabledFlow(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[notificationEnabledKey] ?: true
        }
    }

    suspend fun setNotificationEnabled(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[notificationEnabledKey] = isEnabled
        }
    }

    companion object {
        private val notificationEnabledKey =
            booleanPreferencesKey("notificationEnabledKey")
    }
}
