package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.common.util.requests.AsyncRequest
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumption
import kotlinx.coroutines.flow.Flow

interface GetDailyWaterConsumptionUseCase {
    operator fun invoke(ignore: AsyncRequest.Continuous): Flow<DailyWaterConsumption?>
    suspend operator fun invoke(ignore: AsyncRequest.Single): DailyWaterConsumption?
}

