package com.github.lucascalheiros.waterreminder.data.measuresystemprovider.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.github.lucascalheiros.waterreminder.data.measuresystemprovider.data.converters.toIntValue
import com.github.lucascalheiros.waterreminder.data.measuresystemprovider.data.converters.toMeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.VolumeUnitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class VolumeUnitRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): VolumeUnitRepository {

    override suspend fun getUnit(): MeasureSystemVolumeUnit {
        return dataStore.data.first()[volumePreferenceKey]?.toMeasureSystemVolumeUnit() ?: MeasureSystemVolumeUnit.ML
    }

    override fun getUnitFlow(): Flow<MeasureSystemVolumeUnit> {
        return dataStore.data.map {
            it[volumePreferenceKey]?.toMeasureSystemVolumeUnit() ?: MeasureSystemVolumeUnit.ML
        }
    }

    override suspend fun setUnit(unit: MeasureSystemVolumeUnit) {
        dataStore.edit {
            it[volumePreferenceKey] = unit.toIntValue()
        }
    }

    companion object {
        private val volumePreferenceKey = intPreferencesKey("volumePreferenceKey")
    }
}