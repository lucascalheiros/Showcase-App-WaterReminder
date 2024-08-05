package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.common.util.date.atStartOfDay
import com.github.lucascalheiros.waterreminder.common.util.date.todayLocalDate
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumptionSummary
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDailyWaterConsumptionSummaryUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetTodayWaterConsumptionSummaryUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.SummaryRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus

internal class GetTodayWaterConsumptionSummaryUseCaseImpl(
    private val getDailyWaterConsumptionSummaryUseCase: GetDailyWaterConsumptionSummaryUseCase,
) : GetTodayWaterConsumptionSummaryUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun invoke(): Flow<DailyWaterConsumptionSummary> {
        return todayFlow().flatMapLatest {
            getDailyWaterConsumptionSummaryUseCase(SummaryRequest.SingleDay(it))
        }
    }

    private fun todayFlow() = flow {
        while (true) {
            val today = todayLocalDate()
            emit(today)
            val startOfNextDay = today.plus(1, DateTimeUnit.DAY).atStartOfDay()
            val delayDuration =
                startOfNextDay.toEpochMilliseconds() - Clock.System.now().toEpochMilliseconds()
            delay(delayDuration)
        }
    }.distinctUntilChanged()

}
