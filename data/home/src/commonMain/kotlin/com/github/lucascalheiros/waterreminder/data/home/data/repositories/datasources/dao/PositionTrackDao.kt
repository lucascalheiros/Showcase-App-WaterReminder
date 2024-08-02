package com.github.lucascalheiros.waterreminder.data.home.data.repositories.datasources.dao

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.lucascalheiros.waterreminder.data.home.data.models.PositionSourceType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class PositionTrackDao(
    private val dataStore: DataStore<Preferences>
) {

    fun all(type: PositionSourceType): Flow<List<Long>> {
        return longListFlow(type.toPreferenceKey()).distinctUntilChanged()
    }

    suspend fun save(type: PositionSourceType, sortedIds: List<Long>) {
        saveLongList(type.toPreferenceKey(), sortedIds)
    }

    private fun longListFlow(key: Preferences.Key<String>): Flow<List<Long>> = dataStore.data
        .map { preferences ->
            val longListString = preferences[key] ?: ""
            if (longListString.isEmpty()) {
                emptyList()
            } else {
                longListString.split(",").map { it.toLong() }
            }
        }

    private suspend fun saveLongList(key: Preferences.Key<String>, longList: List<Long>) {
        val longListString = longList.joinToString(",")
        dataStore.edit { preferences ->
            preferences[key] = longListString
        }
    }

    companion object {
        private val WATER_SOURCE_SORTED_IDS = stringPreferencesKey("WATER_SOURCE_SORTED_IDS")
        private val DRINKS_SORTED_IDS = stringPreferencesKey("DRINKS_SORTED_IDS")

        private fun PositionSourceType.toPreferenceKey():  Preferences.Key<String> {
            return when (this) {
                PositionSourceType.Drink -> DRINKS_SORTED_IDS
                PositionSourceType.WaterSource -> WATER_SOURCE_SORTED_IDS
            }
        }
    }

}