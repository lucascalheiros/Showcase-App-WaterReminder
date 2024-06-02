package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings

import com.github.lucascalheiros.waterreminder.common.measuresystem.MeasureSystemUnit
import com.github.lucascalheiros.waterreminder.common.measuresystem.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.common.measuresystem.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AppTheme

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
