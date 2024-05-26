package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.repositories.WaterSourceRepository
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.CreateWaterSourceUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.requests.CreateWaterSourceRequest

internal class CreateWaterSourceUseCaseImpl(
    private val waterSourceRepository: WaterSourceRepository
): CreateWaterSourceUseCase {
    override suspend fun invoke(request: CreateWaterSourceRequest) {
        waterSourceRepository.create(request)
    }
}