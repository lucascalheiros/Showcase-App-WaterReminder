package com.github.lucascalheiros.waterreminder.feature.home.ui.utils

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import java.io.Serializable

data class MeasureSystemVolumeSerializationWrapper(
    val intrinsicValue: Double,
    val volumeUnit: MeasureSystemVolumeUnit
): Serializable {
    fun unwrapped(): MeasureSystemVolume {
        return MeasureSystemVolume.Companion.create(intrinsicValue, volumeUnit)
    }
}