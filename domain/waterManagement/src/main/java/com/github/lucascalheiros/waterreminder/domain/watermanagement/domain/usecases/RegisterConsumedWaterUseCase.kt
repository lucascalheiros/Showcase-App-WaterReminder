package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume

interface RegisterConsumedWaterUseCase {
    suspend operator fun invoke(volume: MeasureSystemVolume, waterSourceType: WaterSourceType)
}