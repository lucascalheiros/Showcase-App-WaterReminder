package com.github.lucascalheiros.waterremindermvp.common.appcore.format

import android.content.Context
import com.github.lucascalheiros.waterremindermvp.common.appcore.R
import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolume
import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolumeUnit

fun MeasureSystemVolume.shortUnitFormatted(context: Context): String {
    return when (volumeUnit()) {
        MeasureSystemVolumeUnit.ML -> context.resources.getString(R.string.short_ml)
        MeasureSystemVolumeUnit.OZ_UK -> context.resources.getString(R.string.short_uk_oz)
        MeasureSystemVolumeUnit.OZ_US -> context.resources.getString(R.string.short_us_oz)
    }
}

fun MeasureSystemVolume.shortValueFormatted(): String {
    return when (volumeUnit()) {
        MeasureSystemVolumeUnit.ML -> "%.0f".format(intrinsicValue())
        MeasureSystemVolumeUnit.OZ_UK -> "%.2f".format(intrinsicValue())
        MeasureSystemVolumeUnit.OZ_US -> "%.2f".format(intrinsicValue())
    }
}