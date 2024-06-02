package com.github.lucascalheiros.waterreminder.feature.history.ui.history

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterreminder.common.util.logError
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ConsumedWater
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.DeleteConsumedWaterUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDailyWaterConsumptionSummaryUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.SummaryRequest
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.adapters.historysections.ConsumptionVolumeFromDay
import com.github.lucascalheiros.waterreminder.feature.history.ui.history.adapters.historysections.HistorySections
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

class HistoryPresenter(
    mainDispatcher: CoroutineDispatcher,
    getDailyWaterConsumptionSummaryUseCase: GetDailyWaterConsumptionSummaryUseCase,
    private val deleteConsumedWaterUseCase: DeleteConsumedWaterUseCase
) : BasePresenter<HistoryContract.View>(mainDispatcher),
    HistoryContract.Presenter {

    private val summaries = getDailyWaterConsumptionSummaryUseCase(SummaryRequest.LastSummaries(7))
    private val chartLocalDates = MutableStateFlow(
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.let { date ->
            (0..6).map { date.minus(it, DateTimeUnit.DAY) }.reversed()
        }
    )
    private val historySections =
        combine(summaries, chartLocalDates) { summaries, chartLocalDates ->
            buildList {
                add(HistorySections.Title)
                if (summaries.isNotEmpty()) {
                    add(HistorySections.ConsumptionChart(
                        summaries.first().expectedIntake,
                        chartLocalDates.map { date ->
                            summaries.find { it.date == date }?.let {
                                ConsumptionVolumeFromDay(it.date, it.consumptionVolumeByType)
                            } ?: ConsumptionVolumeFromDay(date, listOf())
                        }
                    ))
                }
                addAll(
                    summaries.map { summary ->
                        listOf(
                            HistorySections.DayHeader(summary)
                        ) + summary.consumedWaterList
                            .sortedByDescending { it.consumptionTime }
                            .map { HistorySections.ConsumedWaterItem(it) }
                    }.flatten()
                )
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

    override fun onShowMoreClick() {
        TODO("Not yet implemented")
    }

    override fun CoroutineScope.scopedViewUpdate() {
        launch {
            historySections.collectLatest {
                view?.updateHistorySections(it)
            }
        }
    }
}