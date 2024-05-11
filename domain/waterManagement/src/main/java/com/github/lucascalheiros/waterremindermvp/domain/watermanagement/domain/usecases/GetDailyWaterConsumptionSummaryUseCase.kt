package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.DailyWaterConsumptionSummary
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.requests.SummaryRequest
import kotlinx.coroutines.flow.Flow

interface GetDailyWaterConsumptionSummaryUseCase {
    operator fun invoke(data: SummaryRequest.SingleDay): Flow<DailyWaterConsumptionSummary>
    operator fun invoke(data: SummaryRequest.LastSummaries): Flow<List<DailyWaterConsumptionSummary>>
}
