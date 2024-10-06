package com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.data.repositories.datasources

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class FirstUseFlagsDao(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun isDailyIntakeFirstSet(): Boolean {
        return dataStore.data.first()[isDailyIntakeFirstSetPreferenceKey] ?: true
    }

    suspend fun isFirstAccessCompleted(): Boolean {
        return dataStore.data.first()[isFirstAccessCompletedPreferenceKey] ?: false
    }

     fun isFirstAccessCompletedFlow(): Flow<Boolean> {
        return dataStore.data.map {  it[isFirstAccessCompletedPreferenceKey] ?: false }
    }

    suspend fun setDailyIntakeSetFlag(state: Boolean) {
        dataStore.edit {
            it[isDailyIntakeFirstSetPreferenceKey] = state
        }
    }

    suspend fun setFirstAccessCompletedFlag(state: Boolean) {
        dataStore.edit {
            it[isFirstAccessCompletedPreferenceKey] = state
        }
    }

    companion object {
        private val isDailyIntakeFirstSetPreferenceKey = booleanPreferencesKey("isDailyIntakeFirstSetPreferenceKey")
        private val isFirstAccessCompletedPreferenceKey = booleanPreferencesKey("isFirstAccessCompletedPreferenceKey")
    }
}