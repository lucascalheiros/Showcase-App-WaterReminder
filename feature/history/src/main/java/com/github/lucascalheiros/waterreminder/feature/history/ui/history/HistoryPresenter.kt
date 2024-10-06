package com.github.lucascalheiros.waterreminder.feature.history.ui.history

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterreminder.common.util.date.todayLocalDate
import com.github.lucascalheiros.waterreminder.common.util.logError
import com.github.lucascalheiros.waterreminder.domain.history.domain.models.ChartOption
import com.github.lucascalheiros.waterreminder.domain.history.domain.usecases.GetHistoryChartDataUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ConsumedWater
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.DeleteConsumedWaterUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDailyWaterConsumptionSummaryUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.SummaryRequest
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.models.HistorySections
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.minus
import kotlinx.datetime.plus

class HistoryPresenter(
    mainDispatcher: CoroutineDispatcher,
    getDailyWaterConsumptionSummaryUseCase: GetDailyWaterConsumptionSummaryUseCase,
    private val deleteConsumedWaterUseCase: DeleteConsumedWaterUseCase,
    getHistoryChartDataUseCase: GetHistoryChartDataUseCase
) : BasePresenter<HistoryContract.View>(mainDispatcher), HistoryContract.Presenter {

    private val summaries =
        getDailyWaterConsumptionSummaryUseCase(SummaryRequest.LastSummaries(Int.MAX_VALUE))
    private val chartOption = MutableStateFlow(ChartOption.Week)
    private val baselineDate = MutableStateFlow(todayLocalDate())
    @OptIn(ExperimentalCoroutinesApi::class)
    private val historyChartData = combine(chartOption, baselineDate) { chartOption, baselineDate ->
        chartOption to baselineDate
    }.flatMapLatest { (chartOption, baselineDate) ->
        getHistoryChartDataUseCase(chartOption, baselineDate)
    }
    private val historySections =
        combine(summaries, historyChartData) { summaries, historyChartData ->
            buildList {
                if (summaries.isNotEmpty()) {
                    add(HistorySections.ConsumptionChart(historyChartData))
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

    override fun onSelectChartOption(chartOption: ChartOption) {
        baselineDate.value = todayLocalDate()
        this.chartOption.value = chartOption
    }

    override fun onPreviousChartPeriod() {
        when (chartOption.value) {
            ChartOption.Week -> baselineDate.update {
                it.minus(1, DateTimeUnit.WEEK)
            }

            ChartOption.Month -> baselineDate.update {
                it.minus(1, DateTimeUnit.MONTH)
            }

            ChartOption.Year -> baselineDate.update {
                it.minus(1, DateTimeUnit.YEAR)
            }
        }
    }

    override fun onNextChartPeriod() {
        when (chartOption.value) {
            ChartOption.Week -> baselineDate.update {
                it.plus(1, DateTimeUnit.WEEK)
            }

            ChartOption.Month -> baselineDate.update {
                it.plus(1, DateTimeUnit.MONTH)
            }

            ChartOption.Year -> baselineDate.update {
                it.plus(1, DateTimeUnit.YEAR)
            }
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
