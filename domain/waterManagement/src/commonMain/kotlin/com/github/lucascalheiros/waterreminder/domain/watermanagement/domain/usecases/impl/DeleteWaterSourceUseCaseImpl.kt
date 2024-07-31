package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.WaterSourceRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.DeleteWaterSourceUseCase

internal class DeleteWaterSourceUseCaseImpl(
    private val waterSourceRepository: WaterSourceRepository
) : DeleteWaterSourceUseCase {
    override suspend fun invoke(waterSourceId: Long) {
        waterSourceRepository.deleteById(waterSourceId)
    }
}