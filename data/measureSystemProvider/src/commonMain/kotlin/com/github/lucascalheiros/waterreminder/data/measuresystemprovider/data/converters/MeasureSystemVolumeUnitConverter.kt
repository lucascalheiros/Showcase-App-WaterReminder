package com.github.lucascalheiros.waterreminder.data.measuresystemprovider.data.converters

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit

fun MeasureSystemVolumeUnit.toIntValue(): Int {
    return when (this) {
        MeasureSystemVolumeUnit.ML -> 0
        MeasureSystemVolumeUnit.OZ_UK -> 1
        MeasureSystemVolumeUnit.OZ_US -> 2
    }
}

fun Int.toMeasureSystemVolumeUnit(): MeasureSystemVolumeUnit? {
    return when (this) {
        0 -> MeasureSystemVolumeUnit.ML
        1 -> MeasureSystemVolumeUnit.OZ_UK
        2 -> MeasureSystemVolumeUnit.OZ_US
        else -> null
    }
}