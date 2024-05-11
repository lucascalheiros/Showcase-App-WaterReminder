package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models

import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolume

data class DailyWaterConsumption(
    val dailyWaterConsumptionId: Long,
    val expectedVolume: MeasureSystemVolume,
    val date: Long,
)
