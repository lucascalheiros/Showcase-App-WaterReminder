package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.sections.generalsection.models

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemTemperatureUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeightUnit

data class SettingUnits(
    val volumeUnit: MeasureSystemVolumeUnit,
    val temperatureUnit: MeasureSystemTemperatureUnit,
    val weightUnit: MeasureSystemWeightUnit
)
