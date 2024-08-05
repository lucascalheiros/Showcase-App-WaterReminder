package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface DeleteWaterSourceUseCase {
    @NativeCoroutines
    suspend operator fun invoke(waterSourceId: Long)
}
