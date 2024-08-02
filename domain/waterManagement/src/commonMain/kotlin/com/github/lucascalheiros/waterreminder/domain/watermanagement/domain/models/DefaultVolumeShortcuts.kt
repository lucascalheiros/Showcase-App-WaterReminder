package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume

data class DefaultVolumeShortcuts(
    val smallest: MeasureSystemVolume,
    val small: MeasureSystemVolume,
    val medium: MeasureSystemVolume,
    val large: MeasureSystemVolume
)