package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.WaterSource
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.repositories.WaterSourceRepository
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.CreateWaterSourceUseCase

internal class CreateWaterSourceUseCaseImpl(
    private val waterSourceRepository: WaterSourceRepository
): CreateWaterSourceUseCase {
    override suspend fun invoke(waterSource: WaterSource) {
        waterSourceRepository.save(waterSource)
    }
}