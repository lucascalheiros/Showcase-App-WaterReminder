package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumptionSummary
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.SummaryRequest
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface GetDailyWaterConsumptionSummaryUseCase {
    @NativeCoroutines
    operator fun invoke(data: SummaryRequest.SingleDay): Flow<DailyWaterConsumptionSummary>
    @NativeCoroutines
    operator fun invoke(data: SummaryRequest.Interval): Flow<List<DailyWaterConsumptionSummary>>
    @NativeCoroutines
    operator fun invoke(data: SummaryRequest.LastSummaries): Flow<List<DailyWaterConsumptionSummary>>
    @NativeCoroutines
    operator fun invoke(): Flow<List<DailyWaterConsumptionSummary>>
}
