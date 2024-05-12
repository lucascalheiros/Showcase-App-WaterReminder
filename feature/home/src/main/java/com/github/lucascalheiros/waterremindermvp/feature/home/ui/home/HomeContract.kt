package com.github.lucascalheiros.waterremindermvp.feature.home.ui.home

import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.DailyWaterConsumptionSummary
import com.github.lucascalheiros.waterremindermvp.feature.home.ui.shared.adapters.WaterSourceCard
import com.github.lucascalheiros.waterremindermvp.feature.home.ui.shared.adapters.WaterSourceCardsListener

interface HomeContract {
    interface View {
        fun showTodayConsumptionSummary(summary: DailyWaterConsumptionSummary)
        fun showWaterSourceList(waterSource: List<WaterSourceCard>)
        fun showAddWaterSourceBottomSheet()
    }

    interface Presenter: WaterSourceCardsListener
}