package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumption
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.DailyWaterConsumptionRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.SaveDailyWaterConsumptionUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import kotlinx.datetime.Clock

internal class SaveDailyWaterConsumptionUseCaseImpl(
    private val dailyWaterConsumptionRepository: DailyWaterConsumptionRepository
) : SaveDailyWaterConsumptionUseCase {
    override suspend fun invoke(volume: MeasureSystemVolume) {
        dailyWaterConsumptionRepository.save(
            DailyWaterConsumption(
                -1,
                volume.limitVolume(),
                Clock.System.now().toEpochMilliseconds()
            )
        )
    }

    private fun MeasureSystemVolume.limitVolume(): MeasureSystemVolume {
        return toUnit(MeasureSystemVolumeUnit.ML).let {
            MeasureSystemVolume.create(
                intrinsicValue().coerceIn(1.0, 5000.0),
                MeasureSystemVolumeUnit.ML
            ).toUnit(volumeUnit())
        }
    }
}