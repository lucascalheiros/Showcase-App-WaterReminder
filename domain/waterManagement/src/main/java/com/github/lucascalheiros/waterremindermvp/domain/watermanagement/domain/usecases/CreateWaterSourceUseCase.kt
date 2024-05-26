package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.requests.CreateWaterSourceRequest

interface CreateWaterSourceUseCase {
    suspend operator fun invoke(request: CreateWaterSourceRequest)
}
