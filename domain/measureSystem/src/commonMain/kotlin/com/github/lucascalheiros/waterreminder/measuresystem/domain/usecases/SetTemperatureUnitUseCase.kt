package com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemTemperatureUnit

interface SetTemperatureUnitUseCase {
    suspend operator fun invoke(unit: MeasureSystemTemperatureUnit)
}