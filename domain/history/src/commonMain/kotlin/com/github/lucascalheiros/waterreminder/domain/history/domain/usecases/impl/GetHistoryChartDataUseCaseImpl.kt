package com.github.lucascalheiros.waterreminder.domain.history.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.common.util.date.TimeInterval
import com.github.lucascalheiros.waterreminder.common.util.date.YearAndMonth
import com.github.lucascalheiros.waterreminder.common.util.date.atEndOfDayMillis
import com.github.lucascalheiros.waterreminder.common.util.date.atStartOfDayMillis
import com.github.lucascalheiros.waterreminder.domain.history.domain.models.ChartOption
import com.github.lucascalheiros.waterreminder.domain.history.domain.models.ConsumptionVolume
import com.github.lucascalheiros.waterreminder.domain.history.domain.models.HistoryChartData
import com.github.lucascalheiros.waterreminder.domain.history.domain.models.HistoryChartPeriod
import com.github.lucascalheiros.waterreminder.domain.history.domain.usecases.GetHistoryChartDataUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ConsumptionVolumeByType
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumptionSummary
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDailyWaterConsumptionSummaryUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDailyWaterConsumptionUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.SummaryRequest
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume.Companion.sumOfAt
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.LocalDate

internal class GetHistoryChartDataUseCaseImpl(
    private val getDailyWaterConsumptionSummaryUseCase: GetDailyWaterConsumptionSummaryUseCase,
    private val getDailyWaterConsumptionUseCase: GetDailyWaterConsumptionUseCase
) : GetHistoryChartDataUseCase {

    override fun invoke(option: ChartOption, baselineDate: LocalDate): Flow<HistoryChartData> {
        val period = HistoryChartPeriod.from(option, baselineDate)
        val request = SummaryRequest.Interval(
            TimeInterval(
                period.startDate.atStartOfDayMillis(), period.endDate.atEndOfDayMillis()
            )
        )
        return combine(
            getDailyWaterConsumptionUseCase(),
            getDailyWaterConsumptionSummaryUseCase(request)
        ) { dailyExpected, summaries ->
            HistoryChartData(
                period,
                summaries.filterAndAggregateByPeriod(period),
                dailyExpected?.expectedVolume
            )
        }
    }

    private fun List<DailyWaterConsumptionSummary>.filterAndAggregateByPeriod(period: HistoryChartPeriod) =
        when (period) {
            is HistoryChartPeriod.Month -> filterAndAggregateConsumptionFromWeekAndMonthPeriod(
                period.dates
            )

            is HistoryChartPeriod.Week -> filterAndAggregateConsumptionFromWeekAndMonthPeriod(period.dates)
            is HistoryChartPeriod.Year -> filterAndAggregateConsumptionFromYearPeriod(period.months)
        }


    private fun List<DailyWaterConsumptionSummary>.filterAndAggregateConsumptionFromWeekAndMonthPeriod(
        dates: List<LocalDate>
    ): List<ConsumptionVolume.FromDay> {
        return dates.map { date ->
            find { it.date == date }?.let { summary ->
                ConsumptionVolume.FromDay(summary.date,
                    summary.consumptionVolumeByType.sortedByDescending { it.volume.intrinsicValue() })
            } ?: ConsumptionVolume.FromDay(date, listOf())
        }
    }

    private fun List<DailyWaterConsumptionSummary>.filterAndAggregateConsumptionFromYearPeriod(
        months: List<YearAndMonth>
    ): List<ConsumptionVolume.FromMonth> =
        groupBy { YearAndMonth(it.date.year, it.date.monthNumber) }
            .mapValues { (_, data) ->
                data.map { it.consumptionVolumeByType }.flatten()
                    .groupBy { it.waterSourceType }.entries.map { (type, list) ->
                        ConsumptionVolumeByType(type, list.sumOfAt(
                            list.firstOrNull()?.volume?.volumeUnit() ?: MeasureSystemVolumeUnit.ML
                        ) { it.volume * type.hydrationFactor })
                    }.sortedByDescending { it.volume.intrinsicValue() }
            }.let { monthToConsumptionMap ->
                months.map {
                    ConsumptionVolume.FromMonth(
                        it, monthToConsumptionMap[it] ?: listOf()
                    )
                }
            }
}
