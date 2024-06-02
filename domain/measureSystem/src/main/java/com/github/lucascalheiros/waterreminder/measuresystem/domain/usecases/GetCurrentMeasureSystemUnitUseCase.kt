package com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemUnit
import kotlinx.coroutines.flow.Flow

interface GetCurrentMeasureSystemUnitUseCase {
    suspend fun single(): MeasureSystemUnit
    operator fun invoke(): Flow<MeasureSystemUnit>
}
