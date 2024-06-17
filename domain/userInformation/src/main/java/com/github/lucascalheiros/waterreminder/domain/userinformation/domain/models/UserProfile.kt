package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeight


data class UserProfile(
    val name: String,
    val weight: MeasureSystemWeight,
    val activityLevel: ActivityLevel,
    val temperatureLevel: AmbienceTemperatureLevel,
)