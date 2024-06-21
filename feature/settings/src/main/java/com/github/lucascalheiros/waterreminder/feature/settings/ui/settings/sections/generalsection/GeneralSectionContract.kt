package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.sections.generalsection

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AppTheme
import com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.sections.generalsection.models.SettingUnits
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit

interface GeneralSectionContract {
    interface View {
        fun showDailyWaterIntakeInputDialog(unit: MeasureSystemVolumeUnit)
        fun setDailyWaterIntake(volume: MeasureSystemVolume)
        fun setUnits(units: SettingUnits)
        fun setTheme(theme: AppTheme)
    }

    interface Presenter {
        fun onDailyWaterIntakeOptionClick()
        fun onDailyWaterIntakeChanged(volumeValue: Double)
        fun onThemeSelected(theme: AppTheme)
    }
}
