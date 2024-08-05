package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSource
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface GetWaterSourceUseCase {
    @NativeCoroutines
    operator fun invoke(): Flow<List<WaterSource>>
}