package com.github.lucascalheiros.waterremindermvp.feature.settings.ui.settings

import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemUnit
import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolume
import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.models.AppTheme

interface SettingsContract {
    interface View {
        fun setDailyWaterIntake(volume: MeasureSystemVolume)
        fun setMeasureSystemUnit(unit: MeasureSystemUnit)
        fun setTheme(theme: AppTheme)
        fun setNotificationEnabledState(state: Boolean)
        fun openManageNotifications()
        fun showDailyWaterIntakeInputDialog(unit: MeasureSystemVolumeUnit)
    }

    interface Presenter {
        fun onDailyWaterIntakeOptionClick()
        fun onDailyWaterIntakeChanged(volumeValue: Double)
        fun onMeasureSystemSelected(unit: MeasureSystemUnit)
        fun onThemeSelected(theme: AppTheme)
        fun onNotificationEnableChanged(state: Boolean)
    }
}
