package com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemTemperatureUnit
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface SetTemperatureUnitUseCase {
    @NativeCoroutines
    suspend operator fun invoke(unit: MeasureSystemTemperatureUnit)
}