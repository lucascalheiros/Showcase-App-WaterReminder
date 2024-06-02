package com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.MeasureSystemUnitRepository
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.RegisterCurrentMeasureSystemUnitUseCase

class RegisterCurrentMeasureSystemUnitUseCaseImpl(
    private val measureSystemUnitRepository: MeasureSystemUnitRepository
): RegisterCurrentMeasureSystemUnitUseCase {
    override suspend fun invoke(measureSystemUnit: MeasureSystemUnit) {
        measureSystemUnitRepository.registerCurrentMeasureSystemUnit(measureSystemUnit)
    }
}