package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.temperaturelevelinput

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AmbienceTemperatureLevel
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemTemperatureUnit

interface TemperatureLevelInputContract {
    interface View {
        fun updateSelected(temperatureLevel: AmbienceTemperatureLevel)
        fun updateTemperatureUnit(unit: MeasureSystemTemperatureUnit)
    }

    interface Presenter {
        fun selectCard(temperatureLevel: AmbienceTemperatureLevel)
        fun setTemperatureUnit(unit: MeasureSystemTemperatureUnit)
    }
}