package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeight
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface SetUserProfileWeightUseCase {
    @NativeCoroutines
    suspend operator fun invoke(weight: MeasureSystemWeight)
}