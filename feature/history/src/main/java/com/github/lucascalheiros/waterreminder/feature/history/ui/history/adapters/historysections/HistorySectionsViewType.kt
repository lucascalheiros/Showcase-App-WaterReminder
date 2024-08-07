package com.github.lucascalheiros.waterreminder.feature.history.ui.history.adapters.historysections

import com.github.lucascalheiros.waterreminder.feature.history.ui.history.models.HistorySections

enum class HistorySectionsViewType(val value: Int) {
    Title(0),
    DayHeader(1),
    ConsumedWaterItem(2),
    ConsumptionChart(3),
    ;

    companion object {
        fun from(value: Int): HistorySectionsViewType? {
            return entries.find { it.value == value }
        }

        fun from(data: HistorySections): HistorySectionsViewType {
            return when (data) {
                is HistorySections.ConsumedWaterItem -> ConsumedWaterItem
                is HistorySections.DayHeader -> DayHeader
                HistorySections.Title -> Title
                is HistorySections.ConsumptionChart -> ConsumptionChart
            }
        }
    }
}