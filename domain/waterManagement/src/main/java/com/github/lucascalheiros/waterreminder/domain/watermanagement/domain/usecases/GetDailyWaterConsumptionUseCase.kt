package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumption
import kotlinx.coroutines.flow.Flow

interface GetDailyWaterConsumptionUseCase {
    operator fun invoke(): Flow<DailyWaterConsumption?>
    suspend fun single(): DailyWaterConsumption?
}

