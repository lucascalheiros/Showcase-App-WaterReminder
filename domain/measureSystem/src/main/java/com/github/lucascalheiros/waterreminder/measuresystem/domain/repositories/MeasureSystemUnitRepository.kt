package com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemUnit
import kotlinx.coroutines.flow.Flow

interface MeasureSystemUnitRepository {
    suspend fun getCurrentMeasureSystemUnit(): MeasureSystemUnit
    fun getCurrentMeasureSystemUnitFlow(): Flow<MeasureSystemUnit>
    suspend fun registerCurrentMeasureSystemUnit(measureSystemUnit: MeasureSystemUnit)
}