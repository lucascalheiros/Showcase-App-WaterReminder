package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.CreateWaterSourceTypeRequest

interface CreateWaterSourceTypeUseCase {
    suspend operator fun invoke(request: CreateWaterSourceTypeRequest)
}
