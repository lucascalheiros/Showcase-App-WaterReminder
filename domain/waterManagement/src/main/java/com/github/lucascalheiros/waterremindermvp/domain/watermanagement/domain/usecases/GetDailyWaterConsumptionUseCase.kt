package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterremindermvp.common.util.requests.AsyncRequest
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.DailyWaterConsumption
import kotlinx.coroutines.flow.Flow

interface GetDailyWaterConsumptionUseCase {
    operator fun invoke(ignore: AsyncRequest.Continuous): Flow<DailyWaterConsumption?>
    suspend operator fun invoke(ignore: AsyncRequest.Single): DailyWaterConsumption?
}

