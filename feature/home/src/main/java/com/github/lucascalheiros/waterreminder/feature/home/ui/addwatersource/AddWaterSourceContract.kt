package com.github.lucascalheiros.waterreminder.feature.home.ui.addwatersource

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume

interface AddWaterSourceContract {
    interface View {
        fun showSelectWaterSourceDialog(waterSourceTypeList: List<WaterSourceType>)
        fun showVolumeInputDialog(unit: MeasureSystemVolumeUnit)
        fun setSelectedVolume(volume: MeasureSystemVolume)
        fun setSelectedWaterSourceType(waterSourceType: WaterSourceType)
        fun dismissBottomSheet()
        fun showOperationErrorToast(event: ErrorEvent)
    }

    interface Presenter {
        fun initialize()
        fun onCancelClick()
        fun onConfirmClick()
        fun onWaterSourceTypeSelected(waterSourceType: WaterSourceType)
        fun onVolumeSelected(volumeValue: Double)
        fun onVolumeOptionClick()
        fun onSelectWaterSourceTypeOptionClick()
    }

    enum class ErrorEvent {
        DataLoadingFailed,
        SaveFailed
    }
}