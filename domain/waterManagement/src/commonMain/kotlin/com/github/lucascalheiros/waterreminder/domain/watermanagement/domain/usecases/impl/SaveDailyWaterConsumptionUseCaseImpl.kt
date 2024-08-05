package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.DailyWaterConsumptionRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.SaveDailyWaterConsumptionUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit

internal class SaveDailyWaterConsumptionUseCaseImpl(
    private val dailyWaterConsumptionRepository: DailyWaterConsumptionRepository
) : SaveDailyWaterConsumptionUseCase {
    override suspend fun invoke(volume: MeasureSystemVolume) {
        dailyWaterConsumptionRepository.save(volume.limitVolume())
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