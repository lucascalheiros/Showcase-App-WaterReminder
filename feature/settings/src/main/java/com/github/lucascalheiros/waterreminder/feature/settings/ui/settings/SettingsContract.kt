package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AmbienceTemperatureLevel
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AppTheme
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.UserProfile
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeight

interface SettingsContract {
    interface View {
        fun setDailyWaterIntake(volume: MeasureSystemVolume)
        fun setMeasureSystemUnit(unit: MeasureSystemUnit)
        fun setTheme(theme: AppTheme)
        fun setNotificationEnabledState(state: Boolean)
        fun openManageNotifications()
        fun showDailyWaterIntakeInputDialog(unit: MeasureSystemVolumeUnit)
        fun setUserProfile(userProfile: UserProfile)
        fun setCalculatedIntake(measureSystemVolume: MeasureSystemVolume)
    }

    interface Presenter {
        fun onDailyWaterIntakeOptionClick()
        fun onDailyWaterIntakeChanged(volumeValue: Double)
        fun onMeasureSystemSelected(unit: MeasureSystemUnit)
        fun onThemeSelected(theme: AppTheme)
        fun onNotificationEnableChanged(state: Boolean)
        fun onManageNotificationsClick()
        fun onUserNameSet(name: String)
        fun onUserWeightSet(weight: MeasureSystemWeight)
        fun onUserActivityLevelSet(activityLevel: Int)
        fun onUserTemperatureLevelSet(temperatureLevel: AmbienceTemperatureLevel)
    }
}
