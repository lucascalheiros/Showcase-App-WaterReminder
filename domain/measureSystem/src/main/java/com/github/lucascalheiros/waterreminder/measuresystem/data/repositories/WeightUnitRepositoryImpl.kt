package com.github.lucascalheiros.waterreminder.measuresystem.data.repositories

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeightUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.WeightUnitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class WeightUnitRepositoryImpl : WeightUnitRepository {

    private val msWeightUnit = MutableStateFlow(MeasureSystemWeightUnit.KILOGRAMS)

    override suspend fun getUnit(): MeasureSystemWeightUnit {
        return msWeightUnit.value
    }

    override fun getUnitFlow(): Flow<MeasureSystemWeightUnit> {
        return msWeightUnit
    }

    override suspend fun setUnit(unit: MeasureSystemWeightUnit) {
        msWeightUnit.value = unit
    }
}