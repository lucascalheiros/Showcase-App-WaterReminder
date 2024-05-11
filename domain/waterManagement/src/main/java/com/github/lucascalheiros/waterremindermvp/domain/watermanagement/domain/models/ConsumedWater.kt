package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models

import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolume


data class ConsumedWater(
    val consumedWaterId: Long,
    val volume: MeasureSystemVolume,
    val consumptionTime: Long,
    val waterSourceType: WaterSourceType?
)
