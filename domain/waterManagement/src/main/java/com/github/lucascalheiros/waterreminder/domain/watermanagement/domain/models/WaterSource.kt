package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume


data class WaterSource(
    val waterSourceId: Long,
    val volume: MeasureSystemVolume,
    val waterSourceType: WaterSourceType
)
