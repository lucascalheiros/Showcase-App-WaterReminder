package com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit

interface SetVolumeUnitUseCase {
    suspend operator fun invoke(unit: MeasureSystemVolumeUnit)
}