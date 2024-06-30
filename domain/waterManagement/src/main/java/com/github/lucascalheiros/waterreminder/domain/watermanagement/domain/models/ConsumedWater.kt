package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume


data class ConsumedWater(
    val consumedWaterId: Long,
    val volume: MeasureSystemVolume,
    val consumptionTime: Long,
    val waterSourceType: WaterSourceType
) {
    val hydrationVolume: MeasureSystemVolume
        get() = volume * waterSourceType.hydrationFactor
}
