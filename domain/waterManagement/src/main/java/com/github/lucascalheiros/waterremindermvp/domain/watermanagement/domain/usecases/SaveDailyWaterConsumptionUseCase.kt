package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolume

interface SaveDailyWaterConsumptionUseCase {
    suspend operator fun invoke(volume: MeasureSystemVolume)
}