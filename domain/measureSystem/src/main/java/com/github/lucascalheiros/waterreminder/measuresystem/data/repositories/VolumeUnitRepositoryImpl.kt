package com.github.lucascalheiros.waterreminder.measuresystem.data.repositories

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.VolumeUnitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class VolumeUnitRepositoryImpl: VolumeUnitRepository {

    private val msVolumeUnit = MutableStateFlow(MeasureSystemVolumeUnit.ML)

    override suspend fun getUnit(): MeasureSystemVolumeUnit {
        return msVolumeUnit.value
    }

    override fun getUnitFlow(): Flow<MeasureSystemVolumeUnit> {
        return msVolumeUnit
    }

    override suspend fun setUnit(unit: MeasureSystemVolumeUnit) {
        msVolumeUnit.value = unit
    }
}