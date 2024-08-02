package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume


data class DefaultAddWaterSourceInfo(
    val volume: MeasureSystemVolume,
    val waterSourceType: WaterSourceType
)