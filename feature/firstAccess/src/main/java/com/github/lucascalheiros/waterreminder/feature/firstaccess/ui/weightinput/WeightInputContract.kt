package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.weightinput

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeightUnit

interface WeightInputContract {
    interface View {
        fun setIntrinsicWeight(weight: Double)
        fun setWeightUnit(weightUnit: MeasureSystemWeightUnit)
    }

    interface Presenter {
        fun setIntrinsicWeight(weight: Double)
        fun setWeightUnit(weightUnit: MeasureSystemWeightUnit)
    }
}