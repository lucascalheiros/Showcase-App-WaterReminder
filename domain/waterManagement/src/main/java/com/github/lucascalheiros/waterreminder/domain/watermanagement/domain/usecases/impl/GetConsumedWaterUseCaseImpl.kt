package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.common.util.logDebug
import com.github.lucascalheiros.waterreminder.common.util.requests.AsyncRequest
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ConsumedWater
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.ConsumedWaterRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetConsumedWaterUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetCurrentMeasureSystemUnitUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.ConsumedWaterRequest
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal class GetConsumedWaterUseCaseImpl(
    private val consumedWaterRepository: ConsumedWaterRepository,
    private val getCurrentMeasureSystemUnitUseCase: GetCurrentMeasureSystemUnitUseCase
) : GetConsumedWaterUseCase {
    override fun invoke(input: ConsumedWaterRequest.FromTimeInterval): Flow<List<ConsumedWater>> {
        logDebug(input.toString())
        val interval = input.interval
        return combine(
            consumedWaterRepository.allFlow()
                .map { consumedWaterList ->
                    logDebug(consumedWaterList.joinToString("\n"))
                    consumedWaterList.filter { it.consumptionTime in interval.startTimestamp..interval.endTimestamp }
                },
            getCurrentMeasureSystemUnitUseCase(AsyncRequest.Continuous)
        ) { list, unit ->
            list.map { it.copy(volume = it.volume.toUnit(unit)) }
        }
    }

    override fun invoke(input: ConsumedWaterRequest.ItemsFromLastDays): Flow<List<ConsumedWater>> {
        return combine(
            consumedWaterRepository.allFlow()
                .map { consumedWaterList ->
                    val uniqueDaysSet = mutableSetOf<LocalDate>()
                    consumedWaterList.takeLastWhile { consumedWater ->
                        Instant.fromEpochMilliseconds(consumedWater.consumptionTime)
                            .toLocalDateTime(TimeZone.currentSystemDefault())
                            .date.let {
                                uniqueDaysSet.add(it)
                            }
                        uniqueDaysSet.size <= input.daysToInclude
                    }
                },
            getCurrentMeasureSystemUnitUseCase(AsyncRequest.Continuous)
        ) { list: List<ConsumedWater>, unit: MeasureSystemUnit ->
            list.map { it.copy(volume = it.volume.toUnit(unit)) }
        }
    }
}