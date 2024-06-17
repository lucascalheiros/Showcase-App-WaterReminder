package com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories.VolumeUnitRepository
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetVolumeUnitUseCase
import kotlinx.coroutines.flow.Flow

class GetVolumeUnitUseCaseImpl(
    private val measureSystemUnitRepository: VolumeUnitRepository
) : GetVolumeUnitUseCase {
    override suspend fun single(): MeasureSystemVolumeUnit {
        return measureSystemUnitRepository.getUnit()
    }

    override fun invoke(): Flow<MeasureSystemVolumeUnit> {
        return measureSystemUnitRepository.getUnitFlow()
    }

}
