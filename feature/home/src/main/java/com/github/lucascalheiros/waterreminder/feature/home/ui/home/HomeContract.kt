package com.github.lucascalheiros.waterreminder.feature.home.ui.home

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumptionSummary
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSource
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.drinkchips.DrinkItems
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.watersource.WaterSourceCard
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit

interface HomeContract {
    interface View {
        fun setTodayConsumptionSummary(summary: DailyWaterConsumptionSummary)
        fun setWaterSourceList(waterSource: List<WaterSourceCard>)
        fun setDrinkList(drinks: List<DrinkItems>)
        fun sendUIEvent(event: ViewUIEvents)
    }

    interface Presenter {
        fun onWaterSourceClick(waterSource: WaterSource)
        fun onAddWaterSourceClick()
        fun onDeleteWaterSourceClick(waterSource: WaterSource)
        fun onDrinkClick(waterSourceType: WaterSourceType)
        fun onAddDrinkClick()
        fun onDeleteDrink(waterSourceType: WaterSourceType)
    }

    sealed interface ViewUIEvents {
        data class OpenDrinkShortcut(
            val type: WaterSourceType,
            val unit: MeasureSystemVolumeUnit
        ) : ViewUIEvents

        data object OpenAddWaterSource : ViewUIEvents

        data object OpenAddDrink : ViewUIEvents
    }
}