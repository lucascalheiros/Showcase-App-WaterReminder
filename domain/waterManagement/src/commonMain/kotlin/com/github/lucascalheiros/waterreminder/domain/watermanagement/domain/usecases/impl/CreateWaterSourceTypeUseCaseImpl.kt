package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.WaterSourceTypeRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.CreateWaterSourceTypeUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.CreateWaterSourceTypeRequest

internal class CreateWaterSourceTypeUseCaseImpl(
    private val waterSourceTypeRepository: WaterSourceTypeRepository
): CreateWaterSourceTypeUseCase {
    override suspend fun invoke(request: CreateWaterSourceTypeRequest) {
        waterSourceTypeRepository.create(request)
    }
}
