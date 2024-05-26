package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.requests

import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolume
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.WaterSourceType

data class CreateWaterSourceRequest(
    val volume: MeasureSystemVolume,
    val waterSourceType: WaterSourceType
)
