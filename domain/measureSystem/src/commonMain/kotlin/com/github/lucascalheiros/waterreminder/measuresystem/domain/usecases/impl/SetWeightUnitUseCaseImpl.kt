package com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeightUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.WeightUnitRepository
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.SetWeightUnitUseCase

class SetWeightUnitUseCaseImpl(
    private val measureSystemUnitRepository: WeightUnitRepository
) : SetWeightUnitUseCase {
    override suspend fun invoke(unit: MeasureSystemWeightUnit) {
        measureSystemUnitRepository.setUnit(unit)
    }
}