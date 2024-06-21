package com.github.lucascalheiros.waterreminder.data.measuresystemprovider.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.github.lucascalheiros.waterreminder.data.measuresystemprovider.data.converters.toIntValue
import com.github.lucascalheiros.waterreminder.data.measuresystemprovider.data.converters.toMeasureSystemTemperatureUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemTemperatureUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.TemperatureUnitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TemperatureUnitRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): TemperatureUnitRepository {

    override suspend fun getUnit(): MeasureSystemTemperatureUnit {
        return dataStore.data.first()[temperaturePreferenceKey]?.toMeasureSystemTemperatureUnit() ?: MeasureSystemTemperatureUnit.Celsius
    }

    override fun getUnitFlow(): Flow<MeasureSystemTemperatureUnit> {
        return dataStore.data.map {
            it[temperaturePreferenceKey]?.toMeasureSystemTemperatureUnit() ?: MeasureSystemTemperatureUnit.Celsius
        }
    }

    override suspend fun setUnit(unit: MeasureSystemTemperatureUnit) {
        dataStore.edit {
            it[temperaturePreferenceKey] = unit.toIntValue()
        }
    }

    companion object {
        private val temperaturePreferenceKey = intPreferencesKey("temperaturePreferenceKey")
    }
}