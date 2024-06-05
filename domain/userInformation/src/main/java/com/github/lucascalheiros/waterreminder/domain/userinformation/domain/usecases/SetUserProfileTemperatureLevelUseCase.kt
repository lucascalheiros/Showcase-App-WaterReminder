package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AmbienceTemperatureLevel

interface SetUserProfileTemperatureLevelUseCase {
    suspend operator fun invoke(temperatureLevel: AmbienceTemperatureLevel)
}