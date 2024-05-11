package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models

data class ConsumptionPercentageByType(
    val waterSourceType: WaterSourceType,
    val percentage: Float
)