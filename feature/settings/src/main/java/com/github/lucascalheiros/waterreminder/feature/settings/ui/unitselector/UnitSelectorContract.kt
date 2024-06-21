package com.github.lucascalheiros.waterreminder.feature.settings.ui.unitselector

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemTemperatureUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeightUnit

interface UnitSelectorContract {
    interface View {
        fun setVolumeUnit(value: MeasureSystemVolumeUnit)
        fun setTemperatureUnit(value: MeasureSystemTemperatureUnit)
        fun setWeightUnit(value: MeasureSystemWeightUnit)
    }

    interface Presenter {
        fun loadData()
        fun onChangeVolumeUnit(value: MeasureSystemVolumeUnit)
        fun onChangeTemperatureUnit(value: MeasureSystemTemperatureUnit)
        fun onChangeWeightUnit(value: MeasureSystemWeightUnit)
    }
}