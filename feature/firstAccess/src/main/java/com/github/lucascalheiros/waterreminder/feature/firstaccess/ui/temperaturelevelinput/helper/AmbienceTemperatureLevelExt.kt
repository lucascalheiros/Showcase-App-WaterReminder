package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.temperaturelevelinput.helper

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AmbienceTemperatureLevel
import com.github.lucascalheiros.waterreminder.feature.firstaccess.R

fun AmbienceTemperatureLevel.titleRes(): Int {
    return when(this) {
        AmbienceTemperatureLevel.Cold -> R.string.temperature_level_cold_title
        AmbienceTemperatureLevel.Moderate -> R.string.temperature_level_moderate_title
        AmbienceTemperatureLevel.Warm -> R.string.temperature_level_warm_title
        AmbienceTemperatureLevel.Hot -> R.string.temperature_level_hot_title
    }

}
fun AmbienceTemperatureLevel.descriptionRes(): Int {
    return when(this) {
        AmbienceTemperatureLevel.Cold -> R.string.temperature_level_cold_description
        AmbienceTemperatureLevel.Moderate -> R.string.temperature_level_moderate_description
        AmbienceTemperatureLevel.Warm -> R.string.temperature_level_warm_description
        AmbienceTemperatureLevel.Hot -> R.string.temperature_level_hot_description
    }
}
