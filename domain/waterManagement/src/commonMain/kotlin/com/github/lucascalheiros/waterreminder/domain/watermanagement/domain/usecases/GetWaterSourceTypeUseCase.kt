package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface GetWaterSourceTypeUseCase {
    @NativeCoroutines
    operator fun invoke(): Flow<List<WaterSourceType>>
    @NativeCoroutines
    suspend operator fun invoke(id: Long): WaterSourceType?
    @NativeCoroutines
    suspend fun single(): List<WaterSourceType>
}