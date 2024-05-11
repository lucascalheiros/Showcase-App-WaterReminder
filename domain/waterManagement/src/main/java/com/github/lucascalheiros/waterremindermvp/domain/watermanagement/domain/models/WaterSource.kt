package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models

import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolume

data class WaterSource(
    val waterSourceId: Long,
    val volume: MeasureSystemVolume,
    val waterSourceType: WaterSourceType?
)
