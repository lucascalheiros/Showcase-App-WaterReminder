package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume


interface SaveDailyWaterConsumptionUseCase {
    suspend operator fun invoke(volume: MeasureSystemVolume)
}