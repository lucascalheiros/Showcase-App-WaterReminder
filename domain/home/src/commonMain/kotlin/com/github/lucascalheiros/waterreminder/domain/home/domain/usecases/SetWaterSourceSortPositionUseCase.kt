package com.github.lucascalheiros.waterreminder.domain.home.domain.usecases

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface SetWaterSourceSortPositionUseCase {
    @NativeCoroutines
    suspend operator fun invoke(id: Long, position: Int)
}