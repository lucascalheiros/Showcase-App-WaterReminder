package com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemTemperatureUnit
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface GetTemperatureUnitUseCase {
    @NativeCoroutines
    suspend fun single(): MeasureSystemTemperatureUnit
    @NativeCoroutines
    operator fun invoke(): Flow<MeasureSystemTemperatureUnit>
}
