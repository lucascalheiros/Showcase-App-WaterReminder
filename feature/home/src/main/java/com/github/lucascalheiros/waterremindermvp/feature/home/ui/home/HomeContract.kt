package com.github.lucascalheiros.waterremindermvp.feature.home.ui.home

import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.DailyWaterConsumptionSummary
import com.github.lucascalheiros.waterremindermvp.feature.home.ui.home.adapters.WaterSourceCard
import com.github.lucascalheiros.waterremindermvp.feature.home.ui.home.adapters.WaterSourceCardsListener

interface HomeContract {
    interface View {
        fun setTodayConsumptionSummary(summary: DailyWaterConsumptionSummary)
        fun setWaterSourceList(waterSource: List<WaterSourceCard>)
        fun showAddWaterSourceBottomSheet()
    }

    interface Presenter: WaterSourceCardsListener
}