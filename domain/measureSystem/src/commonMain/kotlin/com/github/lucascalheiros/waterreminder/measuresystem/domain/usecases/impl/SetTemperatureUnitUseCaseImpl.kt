package com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemTemperatureUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.TemperatureUnitRepository
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.SetTemperatureUnitUseCase

class SetTemperatureUnitUseCaseImpl(
    private val measureSystemUnitRepository: TemperatureUnitRepository
) : SetTemperatureUnitUseCase {
    override suspend fun invoke(unit: MeasureSystemTemperatureUnit) {
        measureSystemUnitRepository.setUnit(unit)
    }
}