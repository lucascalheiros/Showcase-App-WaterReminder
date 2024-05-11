package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolume

interface RegisterConsumedWaterUseCase {
    suspend operator fun invoke(volume: MeasureSystemVolume, waterSourceType: WaterSourceType)
}