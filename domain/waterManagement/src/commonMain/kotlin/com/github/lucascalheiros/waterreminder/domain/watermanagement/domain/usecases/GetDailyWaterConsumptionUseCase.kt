package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumption
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface GetDailyWaterConsumptionUseCase {
    @NativeCoroutines
    operator fun invoke(): Flow<DailyWaterConsumption?>
    @NativeCoroutines
    suspend fun single(): DailyWaterConsumption?
}

