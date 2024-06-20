package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.confirmationinput

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit

interface ConfirmationInputContract {
    interface View {
        fun updateVolumeUnit(volumeUnit: MeasureSystemVolumeUnit)
        fun updateVolume(volume: MeasureSystemVolume)
        fun showVolumeInputDialog(volumeUnit: MeasureSystemVolumeUnit)
    }

    interface Presenter {
        fun loadData()
        fun onVolumeClick()
        fun onVolumeUnitSelected(volumeUnit: MeasureSystemVolumeUnit)
        fun onVolumeSelected(volumeInMl: Double)
    }
}