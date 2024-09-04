package com.github.lucascalheiros.waterreminder.domain.history.domain.models

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume

data class HistoryChartData(
    val chartPeriod: HistoryChartPeriod,
    val consumptionVolume: List<ConsumptionVolume>,
    val volumeIntake: MeasureSystemVolume?
)