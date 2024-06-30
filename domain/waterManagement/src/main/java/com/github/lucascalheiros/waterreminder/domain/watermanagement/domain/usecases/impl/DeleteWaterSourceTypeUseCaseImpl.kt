package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.WaterSourceTypeRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.DeleteWaterSourceTypeUseCase

internal class DeleteWaterSourceTypeUseCaseImpl(
    private val waterSourceTypeRepository: WaterSourceTypeRepository
): DeleteWaterSourceTypeUseCase {
    override suspend fun invoke(waterSourceId: Long) {
        waterSourceTypeRepository.deleteById(waterSourceId)
    }
}
