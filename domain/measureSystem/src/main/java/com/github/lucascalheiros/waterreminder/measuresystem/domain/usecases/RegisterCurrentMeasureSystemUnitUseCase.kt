package com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemUnit

interface RegisterCurrentMeasureSystemUnitUseCase {
    suspend operator fun invoke(measureSystemUnit: MeasureSystemUnit)
}