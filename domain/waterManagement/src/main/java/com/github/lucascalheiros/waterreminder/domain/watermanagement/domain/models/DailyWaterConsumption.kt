package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models

import com.github.lucascalheiros.waterreminder.common.measuresystem.MeasureSystemVolume

data class DailyWaterConsumption(
    val dailyWaterConsumptionId: Long,
    val expectedVolume: MeasureSystemVolume,
    val date: Long,
)
