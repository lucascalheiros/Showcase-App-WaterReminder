package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumption
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.DailyWaterConsumptionRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.SaveDailyWaterConsumptionUseCase
import com.github.lucascalheiros.waterreminder.common.measuresystem.MeasureSystemVolume
import kotlinx.datetime.Clock

internal class SaveDailyWaterConsumptionUseCaseImpl(
    private val dailyWaterConsumptionRepository: DailyWaterConsumptionRepository
): SaveDailyWaterConsumptionUseCase {
    override suspend fun invoke(volume: MeasureSystemVolume) {
        dailyWaterConsumptionRepository.save(
            DailyWaterConsumption(
                -1,
                volume,
                Clock.System.now().toEpochMilliseconds()
            )
        )
    }
}