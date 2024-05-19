package com.github.lucascalheiros.waterremindermvp.feature.history.ui.history.adapters.historysections

enum class HistorySectionsViewType(val value: Int) {
    Title(0),
    DayHeader(1),
    ConsumedWaterItem(2),
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
            }
        }
    }
}