package com.github.lucascalheiros.waterreminder.feature.history.ui.history

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ConsumedWater
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.models.HistorySections

class HistoryContract {
    interface View {
        fun updateHistorySections(sectionsData: List<HistorySections>)
    }

    interface Presenter {
        fun onSelectChartOption(chartOptions: ChartOptions)
        fun onPreviousChartPeriod()
        fun onNextChartPeriod()
        fun onDeleteAction(consumedWater: ConsumedWater)
    }
}

enum class ChartOptions {
    Week,
    Month,
    Year
}