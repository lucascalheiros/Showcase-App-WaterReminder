package com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import kotlinx.coroutines.flow.Flow

interface GetVolumeUnitUseCase {
    suspend fun single(): MeasureSystemVolumeUnit
    operator fun invoke(): Flow<MeasureSystemVolumeUnit>
}
