package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume


data class DailyWaterConsumption(
    val dailyWaterConsumptionId: Long,
    val expectedVolume: MeasureSystemVolume,
    val date: Long,
)
