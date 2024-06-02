package com.github.lucascalheiros.waterreminder.feature.home.ui.addwatersource

import com.github.lucascalheiros.waterreminder.common.measuresystem.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType

val waterSourceType1 = WaterSourceType(
    1,
    "Water",
    lightColor = 0xFF0000FF,
    darkColor = 0xFFADD8E6,
    1f,
    false
)
val waterSourceType2 = WaterSourceType(
    2,
    "Coffee",
    lightColor = 0xFF0000FF,
    darkColor = 0xFFADD8E6,
    1f,
    false
)
val volumeValue1 = MeasureSystemVolume.create(100.0, com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit.ML)
val volumeValue2 = MeasureSystemVolume.create(150.0, com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit.ML)
