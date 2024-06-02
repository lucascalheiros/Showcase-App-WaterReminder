package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models

import com.github.lucascalheiros.waterreminder.common.measuresystem.MeasureSystemVolume

data class ConsumptionVolumeByType(
    val waterSourceType: WaterSourceType,
    val volume: MeasureSystemVolume
)