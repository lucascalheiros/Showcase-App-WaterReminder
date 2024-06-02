package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume

data class CreateWaterSourceRequest(
    val volume: MeasureSystemVolume,
    val waterSourceType: WaterSourceType
)
