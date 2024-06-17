package com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeightUnit
import kotlinx.coroutines.flow.Flow

interface GetWeightUnitUseCase {
    suspend fun single(): MeasureSystemWeightUnit
    operator fun invoke(): Flow<MeasureSystemWeightUnit>
}
