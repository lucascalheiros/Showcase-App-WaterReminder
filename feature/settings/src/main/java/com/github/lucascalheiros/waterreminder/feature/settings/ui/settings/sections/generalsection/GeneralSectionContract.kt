package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.sections.generalsection

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AppTheme
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit

interface GeneralSectionContract {
    interface View {
        fun showDailyWaterIntakeInputDialog(unit: MeasureSystemVolumeUnit)
        fun setDailyWaterIntake(volume: MeasureSystemVolume)
        fun setMeasureSystemUnit(unit: MeasureSystemVolumeUnit)
        fun setTheme(theme: AppTheme)
    }

    interface Presenter {
        fun onDailyWaterIntakeOptionClick()
        fun onDailyWaterIntakeChanged(volumeValue: Double)
        fun onMeasureSystemSelected(unit: MeasureSystemVolumeUnit)
        fun onThemeSelected(theme: AppTheme)
    }
}
