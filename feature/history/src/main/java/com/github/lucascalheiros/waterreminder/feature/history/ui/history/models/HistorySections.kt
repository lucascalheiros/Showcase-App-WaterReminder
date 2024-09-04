package com.github.lucascalheiros.waterreminder.feature.history.ui.history.models

import com.github.lucascalheiros.waterreminder.domain.history.domain.models.HistoryChartData
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ConsumedWater
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumptionSummary

sealed interface HistorySections {
    data object Title : HistorySections
    data class DayHeader(val summary: DailyWaterConsumptionSummary) : HistorySections
    data class ConsumedWaterItem(val consumedWater: ConsumedWater) : HistorySections
    data class ConsumptionChart(val historyChartData: HistoryChartData) : HistorySections
}

