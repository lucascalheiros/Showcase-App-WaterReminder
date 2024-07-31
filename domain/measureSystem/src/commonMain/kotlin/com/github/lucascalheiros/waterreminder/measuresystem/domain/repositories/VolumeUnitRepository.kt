package com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import kotlinx.coroutines.flow.Flow

interface VolumeUnitRepository {
    suspend fun getUnit(): MeasureSystemVolumeUnit
    fun getUnitFlow(): Flow<MeasureSystemVolumeUnit>
    suspend fun setUnit(unit: MeasureSystemVolumeUnit)
}