package com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface GetVolumeUnitUseCase {
    @NativeCoroutines
    suspend fun single(): MeasureSystemVolumeUnit
    @NativeCoroutines
    operator fun invoke(): Flow<MeasureSystemVolumeUnit>
}
