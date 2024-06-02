package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models

import com.github.lucascalheiros.waterreminder.common.measuresystem.MeasureSystemVolume


data class DefaultAddWaterSourceInfo(
    val volume: MeasureSystemVolume,
    val waterSourceType: WaterSourceType
)