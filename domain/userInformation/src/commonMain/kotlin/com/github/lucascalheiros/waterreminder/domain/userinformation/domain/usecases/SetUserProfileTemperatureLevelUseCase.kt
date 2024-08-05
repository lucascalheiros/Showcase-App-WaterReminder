package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AmbienceTemperatureLevel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface SetUserProfileTemperatureLevelUseCase {
    @NativeCoroutines
    suspend operator fun invoke(temperatureLevel: AmbienceTemperatureLevel)
}