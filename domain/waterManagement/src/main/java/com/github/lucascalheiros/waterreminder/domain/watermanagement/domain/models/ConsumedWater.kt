package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models

import com.github.lucascalheiros.waterreminder.common.measuresystem.MeasureSystemVolume


data class ConsumedWater(
    val consumedWaterId: Long,
    val volume: MeasureSystemVolume,
    val consumptionTime: Long,
    val waterSourceType: WaterSourceType
)
