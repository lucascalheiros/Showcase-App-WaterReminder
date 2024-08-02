package com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemTemperatureUnit
import kotlinx.coroutines.flow.Flow

interface GetTemperatureUnitUseCase {
    suspend fun single(): MeasureSystemTemperatureUnit
    operator fun invoke(): Flow<MeasureSystemTemperatureUnit>
}
