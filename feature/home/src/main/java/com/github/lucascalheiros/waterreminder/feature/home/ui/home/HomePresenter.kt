package com.github.lucascalheiros.waterreminder.feature.home.ui.home

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterreminder.common.util.date.atStartOfDay
import com.github.lucascalheiros.waterreminder.common.util.date.todayLocalDate
import com.github.lucascalheiros.waterreminder.common.util.logDebug
import com.github.lucascalheiros.waterreminder.common.util.logError
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.GetSortedDrinkUseCase
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.GetSortedWaterSourceUseCase
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.SetDrinkSortPositionUseCase
import com.github.lucascalheiros.waterreminder.domain.home.domain.usecases.SetWaterSourceSortPositionUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSource
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.DeleteWaterSourceTypeUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.DeleteWaterSourceUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDailyWaterConsumptionSummaryUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.RegisterConsumedWaterUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.SummaryRequest
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.drinkchips.DrinkItems
import com.github.lucascalheiros.waterreminder.feature.home.ui.home.adapters.watersource.WaterSourceCard
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetVolumeUnitUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus


class HomePresenter(
    mainDispatcher: CoroutineDispatcher,
    getDailyWaterConsumptionSummaryUseCase: GetDailyWaterConsumptionSummaryUseCase,
    private val registerConsumedWaterUseCase: RegisterConsumedWaterUseCase,
    private val deleteWaterSourceUseCase: DeleteWaterSourceUseCase,
    private val getVolumeUnitUseCase: GetVolumeUnitUseCase,
    private val deleteWaterSourceTypeUseCase: DeleteWaterSourceTypeUseCase,
    getSortedDrinkUseCase: GetSortedDrinkUseCase,
    getSortedWaterSourceUseCase: GetSortedWaterSourceUseCase,
    private val setDrinkSortPositionUseCase: SetDrinkSortPositionUseCase,
    private val setWaterSourceSortPositionUseCase: SetWaterSourceSortPositionUseCase,
    ) : BasePresenter<HomeContract.View>(mainDispatcher),
    HomeContract.Presenter {

    private val waterSourceModelList = getSortedWaterSourceUseCase().map { waterSourceList ->
        waterSourceList.map { WaterSourceCard.ConsumptionItem(it) } +
                listOf(WaterSourceCard.AddItem)
    }

    private val drinkItemsList = getSortedDrinkUseCase().map { waterSourceTypeList ->
        waterSourceTypeList.map { DrinkItems.OptionItem(it) } +
                listOf(DrinkItems.AddItem)
    }

    private val forceTriggerTodayCheck = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val todayFlow = forceTriggerTodayCheck.flatMapLatest {
        todayFlow()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dailySummary = todayFlow.flatMapLatest {
        getDailyWaterConsumptionSummaryUseCase(SummaryRequest.SingleDay(it))
    }

    /**
     *  Reasonable due to possible locale changes
     */
    override fun updateTodayIfNeeded() {
        forceTriggerTodayCheck.update { it + 1 }
    }

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

    override fun onMoveWaterSourceToPosition(waterSource: WaterSource, position: Int) {
        viewModelScope.launch {
            setWaterSourceSortPositionUseCase(waterSource.waterSourceId, position)
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

    override fun onMoveDrinkToPosition(waterSourceType: WaterSourceType, position: Int) {
        viewModelScope.launch {
            setDrinkSortPositionUseCase(waterSourceType.waterSourceTypeId, position)
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

    private fun todayFlow() = flow {
        while (true) {
            val today = todayLocalDate()
            emit(today)
            val startOfNextDay = today.plus(1, DateTimeUnit.DAY).atStartOfDay()
            val delayDuration =
                startOfNextDay.toEpochMilliseconds() - Clock.System.now().toEpochMilliseconds()
            delay(delayDuration)
        }
    }.distinctUntilChanged()

}
