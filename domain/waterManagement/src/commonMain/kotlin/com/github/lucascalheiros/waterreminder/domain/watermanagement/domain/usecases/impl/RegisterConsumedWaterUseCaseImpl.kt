package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.ConsumedWaterRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.RegisterConsumedWaterUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume

internal class RegisterConsumedWaterUseCaseImpl(
    private val consumedWaterRepository: ConsumedWaterRepository
) : RegisterConsumedWaterUseCase {
    override suspend fun invoke(volume: MeasureSystemVolume, waterSourceType: WaterSourceType) {
        consumedWaterRepository.register(volume, waterSourceType)
    }
}