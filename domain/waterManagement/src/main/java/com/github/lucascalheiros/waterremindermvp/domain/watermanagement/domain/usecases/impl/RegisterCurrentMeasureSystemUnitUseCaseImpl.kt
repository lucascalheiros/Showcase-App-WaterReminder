package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemUnit
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.repositories.MeasureSystemUnitRepository
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.RegisterCurrentMeasureSystemUnitUseCase

class RegisterCurrentMeasureSystemUnitUseCaseImpl(
    private val measureSystemUnitRepository: MeasureSystemUnitRepository
): RegisterCurrentMeasureSystemUnitUseCase {
    override suspend fun invoke(measureSystemUnit: MeasureSystemUnit) {
        measureSystemUnitRepository.registerCurrentMeasureSystemUnit(measureSystemUnit)
    }
}