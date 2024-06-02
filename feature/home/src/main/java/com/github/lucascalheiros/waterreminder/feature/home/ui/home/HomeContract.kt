package com.github.lucascalheiros.waterreminder.feature.home.ui.home

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumptionSummary
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSource
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.WaterSourceCard

interface HomeContract {
    interface View {
        fun setTodayConsumptionSummary(summary: DailyWaterConsumptionSummary)
        fun setWaterSourceList(waterSource: List<WaterSourceCard>)
        fun showAddWaterSourceBottomSheet()
    }

    interface Presenter {
        fun onWaterSourceClick(waterSource: WaterSource)
        fun onAddWaterSourceClick()
        fun onDeleteWaterSourceClick(waterSource: WaterSource)
    }
}