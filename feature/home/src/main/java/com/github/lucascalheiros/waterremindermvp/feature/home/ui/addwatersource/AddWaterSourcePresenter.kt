package com.github.lucascalheiros.waterremindermvp.feature.home.ui.addwatersource

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterremindermvp.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolume
import com.github.lucascalheiros.waterremindermvp.common.util.logError
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.requests.CreateWaterSourceRequest
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.CreateWaterSourceUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.GetCurrentMeasureSystemUnitUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.GetDefaultAddWaterSourceInfoUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.GetWaterSourceTypeUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.requests.AsyncRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddWaterSourcePresenter(
    coroutineDispatcher: CoroutineDispatcher,
    private val getWaterSourceTypeUseCase: GetWaterSourceTypeUseCase,
    private val getDefaultAddWaterSourceInfoUseCase: GetDefaultAddWaterSourceInfoUseCase,
    private val getCurrentMeasureSystemUnitUseCase: GetCurrentMeasureSystemUnitUseCase,
    private val createWaterSourceUseCase: CreateWaterSourceUseCase
) : BasePresenter<AddWaterSourceContract.View>(coroutineDispatcher),
    AddWaterSourceContract.Presenter {

    private val selectedVolume = MutableStateFlow<MeasureSystemVolume?>(null)
    private val selectWaterSourceType = MutableStateFlow<WaterSourceType?>(null)
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
                    selectedVolume.value!!,
                    selectWaterSourceType.value!!
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
        selectWaterSourceType.value = waterSourceType
    }

    override fun onVolumeSelected(volumeValue: Double) {
        selectedVolume.update { measureSystemVolume ->
            measureSystemVolume?.volumeUnit()?.let { MeasureSystemVolume.create(volumeValue, it) }
        }
    }

    override fun onVolumeOptionClick() {
        viewModelScope.launch {
            try {
                val unit = getCurrentMeasureSystemUnitUseCase.invoke(AsyncRequest.Single).toVolumeUnit()
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
        selectedVolume.filterNotNull().collectLatest {
            view?.setSelectedVolume(it)
        }
    }

    private fun CoroutineScope.collectSelectedWaterSourceType() = launch {
        selectWaterSourceType.filterNotNull().collectLatest {
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
                    selectedVolume.value = volume
                    selectWaterSourceType.value = waterSourceType
                }
                isInitialized = true
            } catch (e: Exception) {
                logError("::loadData", e)
                emitErrorEvent(AddWaterSourceContract.ErrorEvent.DataLoadingFailed)
                emitDismissEvent()
            }
        }
    }
}