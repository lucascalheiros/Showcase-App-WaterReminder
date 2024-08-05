package com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeightUnit
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface GetWeightUnitUseCase {
    @NativeCoroutines
    suspend fun single(): MeasureSystemWeightUnit
    @NativeCoroutines
    operator fun invoke(): Flow<MeasureSystemWeightUnit>
}
