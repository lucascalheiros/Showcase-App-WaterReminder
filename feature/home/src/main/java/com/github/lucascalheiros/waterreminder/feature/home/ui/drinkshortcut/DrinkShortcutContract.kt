package com.github.lucascalheiros.waterreminder.feature.home.ui.drinkshortcut

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DefaultVolumeShortcuts
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit

interface DrinkShortcutContract {
    interface View {
        fun setSelectedVolume(volume: MeasureSystemVolume)
        fun dismissBottomSheet()
        fun showOperationErrorToast(event: ErrorEvent)
        fun setVolumeShortcuts(value: DefaultVolumeShortcuts)
    }

    interface Presenter {
        fun initialize(waterSourceTypeId: Long)
        fun onCancelClick()
        fun onConfirmClick()
        fun onVolumeSelected(volumeValue: Double)
    }

    enum class ErrorEvent {
        DataLoadingFailed,
        SaveFailed
    }
}
