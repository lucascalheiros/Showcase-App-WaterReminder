package com.github.lucascalheiros.waterreminder.feature.home.ui.home

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterreminder.common.util.date.todayLocalDate
import com.github.lucascalheiros.waterreminder.common.util.logDebug
import com.github.lucascalheiros.waterreminder.common.util.logError
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSource
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.DeleteWaterSourceUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDailyWaterConsumptionSummaryUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetWaterSourceUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.RegisterConsumedWaterUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.SummaryRequest
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.WaterSourceCard
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomePresenter(
    mainDispatcher: CoroutineDispatcher,
    getWaterSourceUseCase: GetWaterSourceUseCase,
    getDailyWaterConsumptionSummaryUseCase: GetDailyWaterConsumptionSummaryUseCase,
    private val registerConsumedWaterUseCase: RegisterConsumedWaterUseCase,
    private val deleteWaterSourceUseCase: DeleteWaterSourceUseCase,
) : BasePresenter<HomeContract.View>(mainDispatcher),
    HomeContract.Presenter {

    private val waterSourceModelList = getWaterSourceUseCase().map { waterSourceList ->
        waterSourceList.map { WaterSourceCard.ConsumptionItem(it) } +
                listOf(WaterSourceCard.AddItem)
    }

    private val dailySummary =
        getDailyWaterConsumptionSummaryUseCase(SummaryRequest.SingleDay(todayLocalDate()))

    override fun onWaterSourceClick(waterSource: WaterSource) {
        viewModelScope.launch {
            try {
                registerConsumedWaterUseCase(waterSource.volume, waterSource.waterSourceType)
            } catch (e: Exception) {
                logDebug("::onWaterSourceClick", e)
            }
        }
    }

    override fun onAddWaterSourceClick() {
        view?.showAddWaterSourceBottomSheet()
    }

    override fun onDeleteWaterSourceClick(waterSource: WaterSource) {
        viewModelScope.launch {
            try {
                deleteWaterSourceUseCase(waterSource.waterSourceId)
            } catch (e: Exception) {
                logError("::onDeleteWaterSourceClick", e)
            }
        }
    }

    override fun CoroutineScope.scopedViewUpdate() {
        collectWaterSourceModelList()
        collectTodayWaterConsumptionSummary()
    }

    private fun CoroutineScope.collectWaterSourceModelList() = launch {
        waterSourceModelList.collectLatest {
            view?.setWaterSourceList(it)
        }
    }

    private fun CoroutineScope.collectTodayWaterConsumptionSummary() = launch {
        dailySummary.collectLatest {
            view?.setTodayConsumptionSummary(it)
        }
    }

}