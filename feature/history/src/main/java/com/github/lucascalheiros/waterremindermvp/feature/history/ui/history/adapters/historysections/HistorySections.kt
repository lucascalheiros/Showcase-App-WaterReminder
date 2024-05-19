package com.github.lucascalheiros.waterremindermvp.feature.history.ui.history.adapters.historysections

import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.ConsumedWater
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.DailyWaterConsumptionSummary

sealed interface HistorySections {
    data object Title : HistorySections
    data class DayHeader(val summary: DailyWaterConsumptionSummary) : HistorySections
    data class ConsumedWaterItem(val consumedWater: ConsumedWater) : HistorySections
}