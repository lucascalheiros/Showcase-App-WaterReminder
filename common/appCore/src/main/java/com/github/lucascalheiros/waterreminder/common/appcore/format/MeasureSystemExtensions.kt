package com.github.lucascalheiros.waterreminder.common.appcore.format

import android.content.Context
import com.github.lucascalheiros.waterreminder.common.appcore.R
import com.github.lucascalheiros.waterreminder.common.measuresystem.MeasureSystemUnit
import com.github.lucascalheiros.waterreminder.common.measuresystem.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.common.measuresystem.MeasureSystemVolumeUnit

fun MeasureSystemVolume.shortUnitFormatted(context: Context): String {
    return volumeUnit().shortUnitFormatted(context)
}

fun MeasureSystemVolume.shortValueFormatted(context: Context): String {
    return when (volumeUnit()) {
        MeasureSystemVolumeUnit.ML -> context.resources.getString(R.string.short_value_ml, intrinsicValue())
        MeasureSystemVolumeUnit.OZ_UK -> context.resources.getString(R.string.short_value_uk_oz, intrinsicValue())
        MeasureSystemVolumeUnit.OZ_US -> context.resources.getString(R.string.short_value_us_oz, intrinsicValue())
    }
}


fun MeasureSystemVolume.shortValueAndUnitFormatted(context: Context): String {
    return when (volumeUnit()) {
        MeasureSystemVolumeUnit.ML -> context.resources.getString(R.string.short_value_and_unit_ml, intrinsicValue())
        MeasureSystemVolumeUnit.OZ_UK -> context.resources.getString(R.string.short_value_and_unit_uk_oz, intrinsicValue())
        MeasureSystemVolumeUnit.OZ_US -> context.resources.getString(R.string.short_value_and_unit_us_oz, intrinsicValue())
    }
}

fun MeasureSystemUnit.localizedName(context: Context): String {
    return when (this) {
        MeasureSystemUnit.SI -> context.resources.getString(R.string.unit_si_name)
        MeasureSystemUnit.UK -> context.resources.getString(R.string.unit_uk_name)
        MeasureSystemUnit.US -> context.resources.getString(R.string.unit_us_name)
    }
}

fun MeasureSystemVolumeUnit.shortUnitFormatted(context: Context): String {
    return when (this) {
        MeasureSystemVolumeUnit.ML -> context.resources.getString(R.string.short_unit_ml)
        MeasureSystemVolumeUnit.OZ_UK -> context.resources.getString(R.string.short_unit_uk_oz)
        MeasureSystemVolumeUnit.OZ_US -> context.resources.getString(R.string.short_unit_us_oz)
    }
}