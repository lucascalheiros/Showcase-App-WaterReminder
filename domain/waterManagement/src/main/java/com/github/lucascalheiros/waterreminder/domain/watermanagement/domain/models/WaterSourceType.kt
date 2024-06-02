package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models

import java.io.Serializable

data class WaterSourceType(
    val waterSourceTypeId: Long,
    val name: String,
    val lightColor: Long,
    val darkColor: Long,
    val hydrationFactor: Float,
    val custom: Boolean
): Serializable