package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests

import com.github.lucascalheiros.waterreminder.common.measuresystem.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType

data class CreateWaterSourceRequest(
    val volume: MeasureSystemVolume,
    val waterSourceType: WaterSourceType
)
