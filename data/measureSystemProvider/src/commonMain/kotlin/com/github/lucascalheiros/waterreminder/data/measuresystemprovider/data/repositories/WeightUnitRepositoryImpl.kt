package com.github.lucascalheiros.waterreminder.data.measuresystemprovider.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.github.lucascalheiros.waterreminder.data.measuresystemprovider.data.converters.toIntValue
import com.github.lucascalheiros.waterreminder.data.measuresystemprovider.data.converters.toMeasureSystemWeightUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeightUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.WeightUnitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class WeightUnitRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : WeightUnitRepository {

    override suspend fun getUnit(): MeasureSystemWeightUnit {
        return dataStore.data.first()[weightPreferenceKey]?.toMeasureSystemWeightUnit() ?: MeasureSystemWeightUnit.KILOGRAMS
    }

    override fun getUnitFlow(): Flow<MeasureSystemWeightUnit> {
        return dataStore.data.map {
            it[weightPreferenceKey]?.toMeasureSystemWeightUnit() ?: MeasureSystemWeightUnit.KILOGRAMS
        }
    }

    override suspend fun setUnit(unit: MeasureSystemWeightUnit) {
        dataStore.edit {
            it[weightPreferenceKey] = unit.toIntValue()
        }
    }

    companion object {
        private val weightPreferenceKey = intPreferencesKey("weightPreferenceKey")
    }
}