package com.github.lucascalheiros.waterreminder.feature.history.ui.history.adapters.historysections

import com.github.lucascalheiros.waterreminder.common.measuresystem.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ConsumedWater
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ConsumptionVolumeByType
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumptionSummary
import kotlinx.datetime.LocalDate

sealed interface HistorySections {
    data object Title : HistorySections
    data class DayHeader(val summary: DailyWaterConsumptionSummary) : HistorySections
    data class ConsumedWaterItem(val consumedWater: ConsumedWater) : HistorySections
    data class ConsumptionChart(
        val volumeIntake: MeasureSystemVolume,
        val consumptionVolumeFromDay: List<ConsumptionVolumeFromDay>
    ) : HistorySections
}

data class ConsumptionVolumeFromDay(
    val date: LocalDate,
    val consumptionVolumeByType: List<ConsumptionVolumeByType>
)