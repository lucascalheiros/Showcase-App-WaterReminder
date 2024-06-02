package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AppTheme
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume

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
        fun onManageNotificationsClick()
    }
}
