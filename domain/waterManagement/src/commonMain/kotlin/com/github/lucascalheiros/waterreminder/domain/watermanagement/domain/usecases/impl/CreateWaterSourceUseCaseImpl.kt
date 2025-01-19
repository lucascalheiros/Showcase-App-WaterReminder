package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.WaterSourceRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.CreateWaterSourceUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.CreateWaterSourceRequest
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit

internal class CreateWaterSourceUseCaseImpl(
    private val waterSourceRepository: WaterSourceRepository,
) : CreateWaterSourceUseCase {
    override suspend fun invoke(request: CreateWaterSourceRequest) {
        val isDuplicated = waterSourceRepository.all().any {
            it.waterSourceType == request.waterSourceType &&
                    it.volume.toUnit(MeasureSystemVolumeUnit.ML) == request.volume.toUnit(MeasureSystemVolumeUnit.ML)
        }
        if (!isDuplicated) {
            waterSourceRepository.create(request)
        }
    }
}