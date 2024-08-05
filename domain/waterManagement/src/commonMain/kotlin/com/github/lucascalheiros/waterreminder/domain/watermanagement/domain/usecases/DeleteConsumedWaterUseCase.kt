package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface DeleteConsumedWaterUseCase {
    @NativeCoroutines
    suspend operator fun invoke(consumedWaterId: Long)
}
