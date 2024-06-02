package com.github.lucascalheiros.waterreminder.measuresystem.data.repositories

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.MeasureSystemUnitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MeasureSystemUnitRepositoryImpl:
    MeasureSystemUnitRepository {

    private val measureSystemUnit = MutableStateFlow(MeasureSystemUnit.SI)

    override suspend fun getCurrentMeasureSystemUnit(): MeasureSystemUnit {
        return measureSystemUnit.value
    }

    override fun getCurrentMeasureSystemUnitFlow(): Flow<MeasureSystemUnit> {
        return measureSystemUnit
    }

    override suspend fun registerCurrentMeasureSystemUnit(measureSystemUnit: MeasureSystemUnit) {
        this.measureSystemUnit.value = measureSystemUnit
    }
}