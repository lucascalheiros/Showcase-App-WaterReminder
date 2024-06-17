package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumption
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.DailyWaterConsumptionRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDailyWaterConsumptionUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetVolumeUnitUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

internal class GetDailyWaterConsumptionUseCaseImpl(
    private val dailyWaterConsumptionRepository: DailyWaterConsumptionRepository,
    private val getVolumeUnitUseCase: GetVolumeUnitUseCase
) : GetDailyWaterConsumptionUseCase {
    override fun invoke(): Flow<DailyWaterConsumption?> {
        return combine(
            dailyWaterConsumptionRepository.allFlow().map { it.lastOrNull() },
            getVolumeUnitUseCase()
        ) { dailyWaterConsumption, unit ->
            dailyWaterConsumption?.let {
                it.copy(expectedVolume = it.expectedVolume.toUnit(unit))
            }
        }
    }

    override suspend fun single(): DailyWaterConsumption? {
        val unit = getVolumeUnitUseCase.single()
        return dailyWaterConsumptionRepository.all().lastOrNull()?.let {
            it.copy(expectedVolume = it.expectedVolume.toUnit(unit))
        }
    }
}