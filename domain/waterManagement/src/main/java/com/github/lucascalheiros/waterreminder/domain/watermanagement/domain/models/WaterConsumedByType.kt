package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models

import com.github.lucascalheiros.waterreminder.common.measuresystem.MeasureSystemVolume

data class WaterConsumedByType(
    val waterSourceType: WaterSourceType,
    val volume: MeasureSystemVolume
)