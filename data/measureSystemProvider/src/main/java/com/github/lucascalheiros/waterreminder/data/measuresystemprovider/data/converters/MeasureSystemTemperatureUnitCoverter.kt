package com.github.lucascalheiros.waterreminder.data.measuresystemprovider.data.converters

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemTemperatureUnit

fun MeasureSystemTemperatureUnit.toIntValue(): Int {
    return when (this) {
        MeasureSystemTemperatureUnit.Celsius -> 0
        MeasureSystemTemperatureUnit.Fahrenheit -> 1
    }
}

fun Int.toMeasureSystemTemperatureUnit(): MeasureSystemTemperatureUnit? {
    return when (this) {
        0 -> MeasureSystemTemperatureUnit.Celsius
        1 -> MeasureSystemTemperatureUnit.Fahrenheit
        else -> null
    }
}