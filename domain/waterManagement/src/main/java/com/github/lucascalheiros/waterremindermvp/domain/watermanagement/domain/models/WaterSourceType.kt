package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models

data class WaterSourceType(
    val waterSourceTypeId: Long,
    val name: String,
    val lightColor: Long,
    val darkColor: Long,
    val hydrationFactor: Float,
    val custom: Boolean
)