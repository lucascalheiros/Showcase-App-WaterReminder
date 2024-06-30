package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume.Companion.max
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume.Companion.sumOfAt
import kotlinx.datetime.LocalDate

data class DailyWaterConsumptionSummary(
    val expectedIntake: MeasureSystemVolume,
    val date: LocalDate,
    val consumedWaterList: List<ConsumedWater>
) {

    private val volumeUnit = expectedIntake.volumeUnit()
    val intake by lazy { consumedWaterList.sumOfAt(volumeUnit) { it.hydrationVolume } }
    private val maxIntake by lazy { max(expectedIntake, intake, at = volumeUnit) }
    val percentage by lazy { ((intake / maxIntake) * 100).intrinsicValue().toFloat() }
    val consumptionPercentageByType by lazy {
        consumptionVolumeByType.map {
            ConsumptionPercentageByType(
                it.waterSourceType,
                (it.volume / maxIntake).intrinsicValue().toFloat()
            )
        }
    }
    val consumptionVolumeByType by lazy {
        consumedWaterList.groupBy { it.waterSourceType }.entries.map { (type, list) ->
            ConsumptionVolumeByType(
                type,
                list.sumOfAt(volumeUnit) { it.hydrationVolume }
            )
        }
    }
    val goalAchieved by lazy {
        (expectedIntake - intake).intrinsicValue() <= 0
    }
    val remainingIntake by lazy {
        (expectedIntake - intake).let {
            if (it.intrinsicValue() > 0) it else MeasureSystemVolume.create(
                0.0,
                it.volumeUnit()
            )
        }
    }
}