package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumptionSummary
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface GetTodayWaterConsumptionSummaryUseCase {
    @NativeCoroutines
    operator fun invoke(): Flow<DailyWaterConsumptionSummary>
}
