package com.github.lucascalheiros.waterremindermvp.feature.home.ui.addwatersource

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterremindermvp.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterremindermvp.common.appcore.savedstatehandleproperty.SavedStateHandleProperty.Companion.savedStateProperty
import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolume
import com.github.lucascalheiros.waterremindermvp.common.util.logError
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.CreateWaterSourceUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.GetCurrentMeasureSystemUnitUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.GetDefaultAddWaterSourceInfoUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.GetWaterSourceTypeUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.requests.AsyncRequest
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.requests.CreateWaterSourceRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class AddWaterSourcePresenter(
    coroutineDispatcher: CoroutineDispatcher,
    private val state: SavedStateHandle,
    private val getWaterSourceTypeUseCase: GetWaterSourceTypeUseCase,
    private val getDefaultAddWaterSourceInfoUseCase: GetDefaultAddWaterSourceInfoUseCase,
    private val getCurrentMeasureSystemUnitUseCase: GetCurrentMeasureSystemUnitUseCase,
    private val createWaterSourceUseCase: CreateWaterSourceUseCase
) : BasePresenter<AddWaterSourceContract.View>(coroutineDispatcher),
    AddWaterSourceContract.Presenter {

    private val selectedVolumeProperty =
        state.savedStateProperty<MeasureSystemVolume>(SELECTED_VOLUME_KEY, null)
    private val selectWaterSourceTypeProperty =
        state.savedStateProperty<WaterSourceType>(SELECTED_WATER_SOURCE_TYPE_KEY, null)
    private val dismissEvent = MutableStateFlow<Unit?>(null)
    private val errorEvent = MutableStateFlow<AddWaterSourceContract.ErrorEvent?>(null)
    private var isInitialized = false

    override fun initialize() {
        loadInitializationData()
    }

    override fun onCancelClick() {
        emitDismissEvent()
    }

    override fun onConfirmClick() {
        viewModelScope.launch {
            try {
                val request = CreateWaterSourceRequest(
                    selectedVolumeProperty.value!!,
                    selectWaterSourceTypeProperty.value!!
                )
                createWaterSourceUseCase(request)
            } catch (e: Exception) {
                logError("::onConfirmClick", e)
                emitErrorEvent(AddWaterSourceContract.ErrorEvent.SaveFailed)
            } finally {
                emitDismissEvent()
            }
        }
    }

    override fun onWaterSourceTypeSelected(waterSourceType: WaterSourceType) {
        state[SELECTED_WATER_SOURCE_TYPE_KEY] = waterSourceType
    }

    override fun onVolumeSelected(volumeValue: Double) {
        state[SELECTED_VOLUME_KEY] =
            selectedVolumeProperty.value?.volumeUnit()?.let { MeasureSystemVolume.create(volumeValue, it) }
    }

    override fun onVolumeOptionClick() {
        viewModelScope.launch {
            try {
                val unit =
                    getCurrentMeasureSystemUnitUseCase.invoke(AsyncRequest.Single).toVolumeUnit()
                view?.showVolumeInputDialog(unit)
            } catch (e: Exception) {
                logError("::onVolumeOptionClick", e)
            }
        }
    }

    override fun onSelectWaterSourceTypeOptionClick() {
        viewModelScope.launch {
            try {
                view?.showSelectWaterSourceDialog(getWaterSourceTypeUseCase(AsyncRequest.Single))
            } catch (e: Exception) {
                logError("::onSelectWaterSourceTypeOptionClick", e)
            }
        }
    }

    override fun CoroutineScope.scopedViewUpdate() {
        collectErrorEvent()
        collectSelectedVolume()
        collectSelectedWaterSourceType()
        collectDismissEvent()
    }

    private fun CoroutineScope.collectDismissEvent() = launch {
        dismissEvent.filterNotNull().collectLatest {
            view?.run {
                dismissBottomSheet()
                handleDismissEvent()
            }
        }
    }

    private fun CoroutineScope.collectErrorEvent() = launch {
        errorEvent.filterNotNull().collectLatest {
            view?.run {
                showOperationErrorToast(it)
                handleErrorEvent()
            }
        }
    }

    private fun CoroutineScope.collectSelectedVolume() = launch {
        selectedVolumeProperty.stateFlow.filterNotNull().collectLatest {
            view?.setSelectedVolume(it)
        }
    }

    private fun CoroutineScope.collectSelectedWaterSourceType() = launch {
        selectWaterSourceTypeProperty.stateFlow.filterNotNull().collectLatest {
            view?.setSelectedWaterSourceType(it)
        }
    }

    private fun emitDismissEvent() {
        dismissEvent.value = Unit
    }

    private fun handleDismissEvent() {
        dismissEvent.value = null
    }

    private fun emitErrorEvent(event: AddWaterSourceContract.ErrorEvent) {
        errorEvent.value = event
    }

    private fun handleErrorEvent() {
        errorEvent.value = null
    }

    private fun loadInitializationData() {
        viewModelScope.launch {
            if (isInitialized) {
                return@launch
            }
            try {
                with(getDefaultAddWaterSourceInfoUseCase()) {
                    selectedVolumeProperty.set(volume)
                    selectWaterSourceTypeProperty.set(waterSourceType)
                }
                isInitialized = true
            } catch (e: Exception) {
                logError("::loadData", e)
                emitErrorEvent(AddWaterSourceContract.ErrorEvent.DataLoadingFailed)
                emitDismissEvent()
            }
        }
    }

    companion object {
        private const val SELECTED_VOLUME_KEY = "SELECTED_VOLUME_KEY"
        private const val SELECTED_WATER_SOURCE_TYPE_KEY = "SELECTED_WATER_SOURCE_TYPE_KEY"
    }
}