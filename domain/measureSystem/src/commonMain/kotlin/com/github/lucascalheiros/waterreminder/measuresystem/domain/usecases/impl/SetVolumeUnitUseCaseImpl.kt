package com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.VolumeUnitRepository
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.SetVolumeUnitUseCase

class SetVolumeUnitUseCaseImpl(
    private val measureSystemUnitRepository: VolumeUnitRepository
) : SetVolumeUnitUseCase {
    override suspend fun invoke(unit: MeasureSystemVolumeUnit) {
        measureSystemUnitRepository.setUnit(unit)
    }
}