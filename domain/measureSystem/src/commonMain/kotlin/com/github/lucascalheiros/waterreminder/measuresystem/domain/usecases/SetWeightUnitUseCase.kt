package com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeightUnit

interface SetWeightUnitUseCase {
    suspend operator fun invoke(unit: MeasureSystemWeightUnit)
}