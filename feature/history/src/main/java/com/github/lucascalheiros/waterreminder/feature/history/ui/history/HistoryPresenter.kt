package com.github.lucascalheiros.waterreminder.feature.history.ui.history

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterreminder.common.util.logError
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ConsumedWater
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ConsumptionVolumeByType
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumptionSummary
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.DeleteConsumedWaterUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDailyWaterConsumptionSummaryUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.SummaryRequest
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.models.ConsumptionVolume
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.models.HistorySections
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.utils.datesFromMonth
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.utils.datesFromWeek
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume.Companion.sumOfAt
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import java.time.YearMonth
import kotlin.math.max

class HistoryPresenter(
    mainDispatcher: CoroutineDispatcher,
    getDailyWaterConsumptionSummaryUseCase: GetDailyWaterConsumptionSummaryUseCase,
    private val deleteConsumedWaterUseCase: DeleteConsumedWaterUseCase
) : BasePresenter<HistoryContract.View>(mainDispatcher), HistoryContract.Presenter {

    private val summaries =
        getDailyWaterConsumptionSummaryUseCase(SummaryRequest.LastSummaries(Int.MAX_VALUE))
    private val chartOption = MutableStateFlow(ChartOptions.Year)
    private val periodsFromCurrentOption = MutableStateFlow(0)
    private val chartPeriod: Flow<ChartPeriod> =
        combine(chartOption, periodsFromCurrentOption) { chartOption, periodsFromCurrentOption ->
            val currentDate =
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            when (chartOption) {
                ChartOptions.Week -> currentDate.minus(
                    periodsFromCurrentOption, DateTimeUnit.WEEK
                ).let {
                    ChartPeriod.Week(it)
                }

                ChartOptions.Month -> currentDate.minus(
                    periodsFromCurrentOption, DateTimeUnit.MONTH
                ).let {
                    ChartPeriod.Month(YearMonth.of(it.year, it.month))
                }

                ChartOptions.Year -> currentDate.minus(
                    periodsFromCurrentOption, DateTimeUnit.YEAR
                ).let {
                    ChartPeriod.Year(it.year)
                }
            }
        }
    private val historySections = combine(summaries, chartPeriod) { summaries, chartPeriod ->
        buildList {
            add(HistorySections.Title)
            if (summaries.isNotEmpty()) {
                add(summaries.getConsumptionChartFromPeriod(chartPeriod))
            }
            addAll(
                summaries.map { summary ->
                    listOf(
                        HistorySections.DayHeader(summary)
                    ) + summary.consumedWaterList.sortedByDescending { it.consumptionTime }
                        .map { HistorySections.ConsumedWaterItem(it) }
                }.flatten()
            )
        }
    }

    override fun onSelectChartOption(chartOptions: ChartOptions) {
        chartOption.value = chartOptions
    }

    override fun onPreviousChartPeriod() {
        periodsFromCurrentOption.update {
            it + 1
        }
    }

    override fun onNextChartPeriod() {
        periodsFromCurrentOption.update {
            max(it - 1, 0)
        }
    }

    override fun onDeleteAction(consumedWater: ConsumedWater) {
        viewModelScope.launch {
            try {
                deleteConsumedWaterUseCase(consumedWater.consumedWaterId)
            } catch (e: Exception) {
                logError("::onDeleteAction", e)
            }
        }
    }

    override fun CoroutineScope.scopedViewUpdate() {
        launch {
            historySections.collectLatest {
                view?.updateHistorySections(it)
            }
        }
    }
}

private sealed interface ChartPeriod {
    data class Week(val day: LocalDate) : ChartPeriod
    data class Month(val yearMonth: YearMonth) : ChartPeriod
    data class Year(val year: Int) : ChartPeriod
}

private fun List<DailyWaterConsumptionSummary>.filterAndAggregateConsumptionFromWeekAndMonthPeriod(
    chartPeriod: ChartPeriod
): List<ConsumptionVolume.FromDay> {
    val dates = when (chartPeriod) {
        is ChartPeriod.Month -> {
            chartPeriod.yearMonth.datesFromMonth()
        }

        is ChartPeriod.Week -> {
            chartPeriod.day.datesFromWeek()
        }

        else -> {
            listOf()
        }
    }
    return dates.map { date ->
        find { it.date == date }?.let { summary ->
            ConsumptionVolume.FromDay(summary.date,
                summary.consumptionVolumeByType.sortedByDescending { it.volume.intrinsicValue() })
        } ?: ConsumptionVolume.FromDay(date, listOf())
    }
}

private fun List<DailyWaterConsumptionSummary>.getConsumptionChartFromPeriod(chartPeriod: ChartPeriod): HistorySections.ConsumptionChart {
    val intake = first().expectedIntake
    return when (chartPeriod) {
        is ChartPeriod.Month -> HistorySections.ConsumptionChart.Month(
            intake,
            filterAndAggregateConsumptionFromWeekAndMonthPeriod(chartPeriod),
            chartPeriod.yearMonth
        )

        is ChartPeriod.Week -> HistorySections.ConsumptionChart.Week(intake,
            filterAndAggregateConsumptionFromWeekAndMonthPeriod(chartPeriod),
            chartPeriod.day.datesFromWeek().let { it.first() to it.last() })

        is ChartPeriod.Year -> HistorySections.ConsumptionChart.Year(
            intake, filterAndAggregateConsumptionFromYearPeriod(chartPeriod), chartPeriod.year
        )
    }
}

private fun List<DailyWaterConsumptionSummary>.filterAndAggregateConsumptionFromYearPeriod(
    chartPeriod: ChartPeriod.Year
): List<ConsumptionVolume.FromMonth> =
    filter { chartPeriod.year == it.date.year }.groupBy { it.date.month }.mapValues { (_, data) ->
        data.map { it.consumptionVolumeByType }.flatten()
            .groupBy { it.waterSourceType }.entries.map { (type, list) ->
                ConsumptionVolumeByType(type, list.sumOfAt(
                    list.firstOrNull()?.volume?.volumeUnit() ?: MeasureSystemVolumeUnit.ML
                ) { it.volume * type.hydrationFactor })
            }.sortedByDescending { it.volume.intrinsicValue() }
    }.let { monthToConsumptionMap ->
        Month.entries.map {
            ConsumptionVolume.FromMonth(
                it, monthToConsumptionMap[it] ?: listOf()
            )
        }
    }
