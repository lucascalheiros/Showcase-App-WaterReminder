package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories

import com.github.lucascalheiros.waterreminder.common.measuresystem.MeasureSystemUnit
import kotlinx.coroutines.flow.Flow

interface MeasureSystemUnitRepository {
    suspend fun getCurrentMeasureSystemUnit(): MeasureSystemUnit
    fun getCurrentMeasureSystemUnitFlow(): Flow<MeasureSystemUnit>
    suspend fun registerCurrentMeasureSystemUnit(measureSystemUnit: MeasureSystemUnit)
}