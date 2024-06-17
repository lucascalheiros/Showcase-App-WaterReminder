package com.github.lucascalheiros.waterreminder.measuresystem.data.repositories

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemTemperatureUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.TemperatureUnitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class TemperatureUnitRepositoryImpl: TemperatureUnitRepository {

    private val msTemperatureUnit = MutableStateFlow(MeasureSystemTemperatureUnit.Celsius)

    override suspend fun getUnit(): MeasureSystemTemperatureUnit {
        return msTemperatureUnit.value
    }

    override fun getUnitFlow(): Flow<MeasureSystemTemperatureUnit> {
        return msTemperatureUnit
    }

    override suspend fun setUnit(unit: MeasureSystemTemperatureUnit) {
        msTemperatureUnit.value = unit
    }
}