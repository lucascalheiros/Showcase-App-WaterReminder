package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.CreateWaterSourceRequest
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface CreateWaterSourceUseCase {
    @NativeCoroutines
    suspend operator fun invoke(request: CreateWaterSourceRequest)
}
