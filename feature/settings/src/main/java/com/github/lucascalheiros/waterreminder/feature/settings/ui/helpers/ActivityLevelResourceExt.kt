package com.github.lucascalheiros.waterreminder.feature.settings.ui.helpers

import android.content.Context
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AmbienceTemperatureLevel
import com.github.lucascalheiros.waterreminder.feature.settings.R

fun AmbienceTemperatureLevel.displayText(context: Context): String {
    return when (this) {
        AmbienceTemperatureLevel.Cold -> R.string.temperature_level_cold
        AmbienceTemperatureLevel.Moderate -> R.string.temperature_level_moderate
        AmbienceTemperatureLevel.Warm -> R.string.temperature_level_warm
        AmbienceTemperatureLevel.Hot -> R.string.temperature_level_hot
    }.let {
        context.resources.getString(it)
    }
}
