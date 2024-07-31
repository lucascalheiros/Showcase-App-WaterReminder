package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models

data class ConsumptionPercentageByType(
    val waterSourceType: WaterSourceType,
    val percentage: Float
)