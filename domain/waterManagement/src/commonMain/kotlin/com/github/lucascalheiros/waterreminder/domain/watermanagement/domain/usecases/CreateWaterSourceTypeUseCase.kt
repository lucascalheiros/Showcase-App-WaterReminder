package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.CreateWaterSourceTypeRequest
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface CreateWaterSourceTypeUseCase {
    @NativeCoroutines
    suspend operator fun invoke(request: CreateWaterSourceTypeRequest)
}
