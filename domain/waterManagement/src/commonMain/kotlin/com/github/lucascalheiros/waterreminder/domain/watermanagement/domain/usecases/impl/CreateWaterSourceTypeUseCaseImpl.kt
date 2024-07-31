package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.WaterSourceTypeRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.CreateWaterSourceTypeUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.CreateWaterSourceTypeRequest

class CreateWaterSourceTypeUseCaseImpl(
    private val waterSourceTypeRepository: WaterSourceTypeRepository
): CreateWaterSourceTypeUseCase {
    override suspend fun invoke(request: CreateWaterSourceTypeRequest) {
        waterSourceTypeRepository.save(
            WaterSourceType(
                0,
                request.name,
                request.themeAwareColor.onLightColor.toLong(),
                request.themeAwareColor.onDarkColor.toLong(),
                request.hydrationFactor,
                true
            )
        )
    }
}
