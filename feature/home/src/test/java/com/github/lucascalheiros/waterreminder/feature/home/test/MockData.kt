package com.github.lucascalheiros.waterreminder.feature.home.test

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumptionSummary
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DefaultAddDrinkInfo
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DefaultVolumeShortcuts
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ThemeAwareColor
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import kotlinx.datetime.LocalDate

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
val volumeValue1 = MeasureSystemVolume.create(100.0, MeasureSystemVolumeUnit.ML)
val volumeValue2 = MeasureSystemVolume.create(150.0, MeasureSystemVolumeUnit.ML)
val volumeValue3 = MeasureSystemVolume.create(200.0, MeasureSystemVolumeUnit.ML)
val volumeValue4 = MeasureSystemVolume.create(250.0, MeasureSystemVolumeUnit.ML)
val summary1 = DailyWaterConsumptionSummary(
    volumeValue1, LocalDate(2000, 9, 9), listOf()
)
val summary2 = DailyWaterConsumptionSummary(
    volumeValue2, LocalDate(2024, 9, 9), listOf()
)
val defaultVolumeShortcuts1 = DefaultVolumeShortcuts(
    volumeValue1,
    volumeValue2,
    volumeValue3,
    volumeValue4
)
val defaultDrinkInfo1 = DefaultAddDrinkInfo(
    1f,
    ThemeAwareColor(0, 1)
)