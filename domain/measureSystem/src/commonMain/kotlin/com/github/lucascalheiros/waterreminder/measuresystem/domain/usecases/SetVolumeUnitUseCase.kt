package com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface SetVolumeUnitUseCase {
    @NativeCoroutines
    suspend operator fun invoke(unit: MeasureSystemVolumeUnit)
}