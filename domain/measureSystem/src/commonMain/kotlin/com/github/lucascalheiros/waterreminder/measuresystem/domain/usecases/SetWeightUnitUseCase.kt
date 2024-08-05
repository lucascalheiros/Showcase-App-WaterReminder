package com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeightUnit
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface SetWeightUnitUseCase {
    @NativeCoroutines
    suspend operator fun invoke(unit: MeasureSystemWeightUnit)
}