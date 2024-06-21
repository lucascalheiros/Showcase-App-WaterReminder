package com.github.lucascalheiros.waterreminder.data.measuresystemprovider.data.converters

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeightUnit

fun MeasureSystemWeightUnit.toIntValue(): Int {
    return when (this) {
        MeasureSystemWeightUnit.KILOGRAMS -> 0
        MeasureSystemWeightUnit.GRAMS -> 1
        MeasureSystemWeightUnit.POUNDS -> 2
    }
}

fun Int.toMeasureSystemWeightUnit(): MeasureSystemWeightUnit? {
    return when (this) {
        0 -> MeasureSystemWeightUnit.KILOGRAMS
        1 -> MeasureSystemWeightUnit.GRAMS
        2 -> MeasureSystemWeightUnit.POUNDS
        else -> null
    }
}