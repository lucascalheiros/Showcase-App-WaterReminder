package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models

import com.github.lucascalheiros.waterreminder.common.measuresystem.MeasureSystemVolume

data class WaterSource(
    val waterSourceId: Long,
    val volume: MeasureSystemVolume,
    val waterSourceType: WaterSourceType
)
