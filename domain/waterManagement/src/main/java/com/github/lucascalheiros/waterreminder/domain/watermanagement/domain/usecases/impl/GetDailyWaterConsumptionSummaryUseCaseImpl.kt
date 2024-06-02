package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.common.util.date.epochMillisToLocalDate
import com.github.lucascalheiros.waterreminder.common.util.date.toDayTimeInterval
import com.github.lucascalheiros.waterreminder.common.util.requests.AsyncRequest
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumptionSummary
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetConsumedWaterUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDailyWaterConsumptionSummaryUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDailyWaterConsumptionUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.ConsumedWaterRequest
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.SummaryRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapNotNull

class GetDailyWaterConsumptionSummaryUseCaseImpl(
    getDailyWaterConsumptionUseCase: GetDailyWaterConsumptionUseCase,
    private val getConsumedWaterUseCase: GetConsumedWaterUseCase
) : GetDailyWaterConsumptionSummaryUseCase {

    private val expectedWaterConsumption =
        getDailyWaterConsumptionUseCase(AsyncRequest.Continuous)
            .mapNotNull { it?.expectedVolume }.filterNotNull()

    override operator fun invoke(data: SummaryRequest.SingleDay): Flow<DailyWaterConsumptionSummary> {
        return combine(
            expectedWaterConsumption,
            getConsumedWaterUseCase(ConsumedWaterRequest.FromTimeInterval(data.date.toDayTimeInterval()))
        ) { expectedIntake, consumedWaterList ->
            DailyWaterConsumptionSummary(
                expectedIntake,
                data.date,
                consumedWaterList
            )
        }
    }

    override operator fun invoke(data: SummaryRequest.LastSummaries): Flow<List<DailyWaterConsumptionSummary>> {
        return combine(
            expectedWaterConsumption,
            getConsumedWaterUseCase(ConsumedWaterRequest.ItemsFromLastDays(data.itemsToInclude))
        ) { expectedIntake, consumedWaterList ->
            consumedWaterList.map {
                it.consumptionTime.epochMillisToLocalDate()
            }.toSet().reversed().map { date ->
                val input = date.toDayTimeInterval()
                val consumedWaterOfPeriod = consumedWaterList.filter {
                    it.consumptionTime in input.startTimestamp..input.endTimestamp
                }
                DailyWaterConsumptionSummary(
                    expectedIntake,
                    date,
                    consumedWaterOfPeriod
                )
            }
        }
    }
}
