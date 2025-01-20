package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface RegisterConsumedWaterUseCase {
    @NativeCoroutines
    suspend operator fun invoke(volume: MeasureSystemVolume, waterSourceType: WaterSourceType, timestamp: Long)
    @NativeCoroutines
    suspend operator fun invoke(volume: MeasureSystemVolume, waterSourceType: WaterSourceType)
}