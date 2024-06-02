package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.common.measuresystem.MeasureSystemUnit

interface RegisterCurrentMeasureSystemUnitUseCase {
    suspend operator fun invoke(measureSystemUnit: MeasureSystemUnit)
}