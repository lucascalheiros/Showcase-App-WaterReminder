package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterremindermvp.common.util.requests.AsyncRequest
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.DailyWaterConsumption
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.repositories.DailyWaterConsumptionRepository
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.GetCurrentMeasureSystemUnitUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.GetDailyWaterConsumptionUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

internal class GetDailyWaterConsumptionUseCaseImpl(
    private val dailyWaterConsumptionRepository: DailyWaterConsumptionRepository,
    private val getCurrentMeasureSystemUnitUseCase: GetCurrentMeasureSystemUnitUseCase
) : GetDailyWaterConsumptionUseCase {
    override fun invoke(ignore: AsyncRequest.Continuous): Flow<DailyWaterConsumption?> {
        return combine(
            dailyWaterConsumptionRepository.allFlow().map { it.lastOrNull() },
            getCurrentMeasureSystemUnitUseCase(AsyncRequest.Continuous)
        ) { dailyWaterConsumption, unit ->
            dailyWaterConsumption?.let {
                it.copy(expectedVolume = it.expectedVolume.toUnit(unit))
            }
        }
    }

    override suspend fun invoke(ignore: AsyncRequest.Single): DailyWaterConsumption? {
        val unit = getCurrentMeasureSystemUnitUseCase(AsyncRequest.Single)
        return dailyWaterConsumptionRepository.all().lastOrNull()?.let {
            it.copy(expectedVolume = it.expectedVolume.toUnit(unit))
        }
    }
}