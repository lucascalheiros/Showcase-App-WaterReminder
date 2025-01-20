package com.github.lucascalheiros.waterreminder.feature.history.ui.adddrinkentry

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import kotlinx.datetime.LocalDateTime

interface AddDrinkEntryContract {
    interface View {
        fun showSelectWaterSourceDialog(waterSourceTypeList: List<WaterSourceType>)
        fun showVolumeInputDialog(unit: MeasureSystemVolumeUnit)
        fun setSelectedVolume(volume: MeasureSystemVolume)
        fun setSelectedWaterSourceType(waterSourceType: WaterSourceType)
        fun setSelectedDateTime(dateTime: LocalDateTime)
        fun dismissBottomSheet()
        fun showOperationErrorToast(event: ErrorEvent)
        fun showDateTimeInputDialog(dateTime: LocalDateTime)
    }

    interface Presenter {
        fun initialize()
        fun onCancelClick()
        fun onConfirmClick()
        fun onWaterSourceTypeSelected(waterSourceType: WaterSourceType)
        fun onVolumeSelected(volumeValue: Double)
        fun onVolumeOptionClick()
        fun onSelectWaterSourceTypeOptionClick()
        fun onDateTimeClick()
        fun onDateTimeChange(localDateTime: LocalDateTime)
    }

    enum class ErrorEvent {
        DataLoadingFailed,
        SaveFailed
    }
}