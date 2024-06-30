package com.github.lucascalheiros.waterreminder.feature.home.ui.home

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterreminder.common.util.date.todayLocalDate
import com.github.lucascalheiros.waterreminder.common.util.logDebug
import com.github.lucascalheiros.waterreminder.common.util.logError
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSource
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.DeleteWaterSourceTypeUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.DeleteWaterSourceUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDailyWaterConsumptionSummaryUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetWaterSourceTypeUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetWaterSourceUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.RegisterConsumedWaterUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.SummaryRequest
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.drinkchips.DrinkItems
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.watersource.WaterSourceCard
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetVolumeUnitUseCase
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
    getWaterSourceTypeUseCase: GetWaterSourceTypeUseCase,
    private val getVolumeUnitUseCase: GetVolumeUnitUseCase,
    private val deleteWaterSourceTypeUseCase: DeleteWaterSourceTypeUseCase
) : BasePresenter<HomeContract.View>(mainDispatcher),
    HomeContract.Presenter {

    private val waterSourceModelList = getWaterSourceUseCase().map { waterSourceList ->
        waterSourceList.map { WaterSourceCard.ConsumptionItem(it) } +
                listOf(WaterSourceCard.AddItem)
    }

    private val drinkItemsList = getWaterSourceTypeUseCase().map { waterSourceTypeList ->
        waterSourceTypeList.map { DrinkItems.OptionItem(it) } +
                listOf(DrinkItems.AddItem)
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
        view?.sendUIEvent(HomeContract.ViewUIEvents.OpenAddWaterSource)
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

    override fun onDrinkClick(waterSourceType: WaterSourceType) {
        viewModelScope.launch {
            try {
                view?.sendUIEvent(
                    HomeContract.ViewUIEvents.OpenDrinkShortcut(
                        waterSourceType,
                        getVolumeUnitUseCase.single()
                    )
                )
            } catch (e: Exception) {
                logError("::onDrinkClick", e)
            }
        }
    }

    override fun onAddDrinkClick() {
        view?.sendUIEvent(HomeContract.ViewUIEvents.OpenAddDrink)
    }

    override fun onDeleteDrink(waterSourceType: WaterSourceType) {
        viewModelScope.launch {
            try {
                deleteWaterSourceTypeUseCase(waterSourceType.waterSourceTypeId)
            } catch (e: Exception) {
                logError("::onDeleteDrink", e)
            }
        }
    }

    override fun CoroutineScope.scopedViewUpdate() {
        collectWaterSourceModelList()
        collectTodayWaterConsumptionSummary()
        collectDrinks()
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

    private fun CoroutineScope.collectDrinks() = launch {
        drinkItemsList.collectLatest {
            view?.setDrinkList(it)
        }
    }

}