package com.github.lucascalheiros.waterremindermvp.feature.history.ui.history

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterremindermvp.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterremindermvp.common.util.logError
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.ConsumedWater
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.DeleteConsumedWaterUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.GetDailyWaterConsumptionSummaryUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.requests.SummaryRequest
import com.github.lucascalheiros.waterremindermvp.feature.history.ui.history.adapters.historysections.HistorySections
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HistoryPresenter(
    mainDispatcher: CoroutineDispatcher,
    getDailyWaterConsumptionSummaryUseCase: GetDailyWaterConsumptionSummaryUseCase,
    private val deleteConsumedWaterUseCase: DeleteConsumedWaterUseCase
) : BasePresenter<HistoryContract.View>(mainDispatcher),
    HistoryContract.Presenter {

    private val summaries = getDailyWaterConsumptionSummaryUseCase(SummaryRequest.LastSummaries(7))
    private val historySections = summaries.map { summaries ->
        listOf(
            HistorySections.Title
        ) + summaries.map { summary ->
            listOf(
                HistorySections.DayHeader(summary)
            ) + summary.consumedWaterList
                .sortedByDescending { it.consumptionTime }
                .map { HistorySections.ConsumedWaterItem(it) }
        }.flatten()
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