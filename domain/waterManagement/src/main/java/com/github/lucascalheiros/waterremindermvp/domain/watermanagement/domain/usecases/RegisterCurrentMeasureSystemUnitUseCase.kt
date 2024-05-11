package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemUnit

interface RegisterCurrentMeasureSystemUnitUseCase {
    suspend operator fun invoke(measureSystemUnit: MeasureSystemUnit)
}