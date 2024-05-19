package com.github.lucascalheiros.waterremindermvp.feature.history.ui.history

import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.ConsumedWater
import com.github.lucascalheiros.waterremindermvp.feature.history.ui.history.adapters.historysections.HistorySections

class HistoryContract {
    interface View {
        fun updateHistorySections(sectionsData: List<HistorySections>)
    }

    interface Presenter {
        fun onDeleteAction(consumedWater: ConsumedWater)
        fun onShowMoreClick()
    }
}