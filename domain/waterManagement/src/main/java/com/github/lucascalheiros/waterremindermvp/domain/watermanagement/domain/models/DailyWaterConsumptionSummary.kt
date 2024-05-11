package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models

import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolume
import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolume.Companion.max
import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolume.Companion.sumOfAt
import kotlinx.datetime.LocalDate

data class DailyWaterConsumptionSummary(
    val expectedIntake: MeasureSystemVolume,
    val date: LocalDate,
    val consumedWaterList: List<ConsumedWater>
) {

    private val volumeUnit = expectedIntake.volumeUnit()
    val intake by lazy { consumedWaterList.sumOfAt(volumeUnit) { it.volume } }
    private val maxIntake by lazy { max(expectedIntake, intake, at = volumeUnit) }
    val percentage by lazy { ((intake / maxIntake) * 100).intrinsicValue().toFloat() }
    val consumptionPercentageByType by lazy {
        consumedWaterList.groupBy { it.waterSourceType }.entries.mapNotNull { (type, list) ->
            if (type != null) {
                ConsumptionPercentageByType(
                    type,
                    (list.sumOfAt(volumeUnit) { it.volume } / maxIntake).intrinsicValue().toFloat())
            } else null
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