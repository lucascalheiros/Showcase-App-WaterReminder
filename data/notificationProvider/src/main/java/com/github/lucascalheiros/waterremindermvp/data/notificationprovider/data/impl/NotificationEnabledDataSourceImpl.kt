package com.github.lucascalheiros.waterremindermvp.data.notificationprovider.data.impl

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.github.lucascalheiros.waterremindermvp.data.notificationprovider.data.NotificationEnabledDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

internal class NotificationEnabledDataSourceImpl(
    private val context: Context
) : NotificationEnabledDataSource {

    override suspend fun isNotificationEnabled(): Boolean {
        return context.dataStore.data.first()[notificationEnabledKey] ?: true
    }

    override fun isNotificationEnabledFlow(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[notificationEnabledKey] ?: true
        }
    }

    override suspend fun setNotificationEnabled(isEnabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[notificationEnabledKey] = isEnabled
        }
    }

    companion object {
        private val notificationEnabledKey =
            booleanPreferencesKey("notificationEnabledKey")
    }
}
