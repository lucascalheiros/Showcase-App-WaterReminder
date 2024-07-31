package com.github.lucascalheiros.waterreminder.feature.history.ui.history.models

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ConsumedWater
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumptionSummary
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.ChartOptions
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import kotlinx.datetime.LocalDate
import java.time.YearMonth

sealed interface HistorySections {
    data object Title : HistorySections
    data class DayHeader(val summary: DailyWaterConsumptionSummary) : HistorySections
    data class ConsumedWaterItem(val consumedWater: ConsumedWater) : HistorySections
    sealed interface ConsumptionChart : HistorySections {
        val volumeIntake: MeasureSystemVolume
        val consumptionVolume: List<ConsumptionVolume>
        val chartPeriodOption: ChartOptions

        data class Week(
            override val volumeIntake: MeasureSystemVolume,
            override val consumptionVolume: List<ConsumptionVolume.FromDay>,
            val dateRange: Pair<LocalDate, LocalDate>
        ) : ConsumptionChart {
            override val chartPeriodOption: ChartOptions = ChartOptions.Week
        }

        data class Month(
            override val volumeIntake: MeasureSystemVolume,
            override val consumptionVolume: List<ConsumptionVolume.FromDay>,
            val yearMonth: YearMonth
        ) : ConsumptionChart {
            override val chartPeriodOption: ChartOptions = ChartOptions.Month
        }

        data class Year(
            override val volumeIntake: MeasureSystemVolume,
            override val consumptionVolume: List<ConsumptionVolume.FromMonth>,
            val year: Int
        ) : ConsumptionChart {
            override val chartPeriodOption: ChartOptions = ChartOptions.Year
        }
    }
}

