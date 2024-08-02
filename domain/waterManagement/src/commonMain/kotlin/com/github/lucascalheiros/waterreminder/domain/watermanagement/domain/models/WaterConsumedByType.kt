package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume


data class WaterConsumedByType(
    val waterSourceType: WaterSourceType,
    val volume: MeasureSystemVolume
)