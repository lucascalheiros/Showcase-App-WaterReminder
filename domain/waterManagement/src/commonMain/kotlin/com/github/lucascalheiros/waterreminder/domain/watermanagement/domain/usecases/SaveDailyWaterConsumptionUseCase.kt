package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface SaveDailyWaterConsumptionUseCase {
    @NativeCoroutines
    suspend operator fun invoke(volume: MeasureSystemVolume)
}