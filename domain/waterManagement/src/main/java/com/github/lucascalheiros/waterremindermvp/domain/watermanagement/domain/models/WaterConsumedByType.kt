package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models

import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolume

data class WaterConsumedByType(
    val waterSourceType: WaterSourceType,
    val volume: MeasureSystemVolume
)