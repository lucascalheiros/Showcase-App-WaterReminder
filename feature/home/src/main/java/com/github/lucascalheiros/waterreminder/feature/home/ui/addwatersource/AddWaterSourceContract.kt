package com.github.lucascalheiros.waterreminder.feature.home.ui.addwatersource

import com.github.lucascalheiros.waterreminder.common.measuresystem.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.common.measuresystem.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType

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