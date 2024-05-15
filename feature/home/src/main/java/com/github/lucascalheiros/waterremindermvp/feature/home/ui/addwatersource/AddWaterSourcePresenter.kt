package com.github.lucascalheiros.waterremindermvp.feature.home.ui.addwatersource

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterremindermvp.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolume
import com.github.lucascalheiros.waterremindermvp.common.util.logError
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.WaterSource
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.CreateWaterSourceUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.GetCurrentMeasureSystemUnitUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.GetDefaultAddWaterSourceInfoUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.GetWaterSourceTypeUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.requests.AsyncRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
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

    init {
        loadData()
    }

    override fun onCancelClick() {
        emitDismissEvent()
    }

    override fun onConfirmClick() {
        viewModelScope.launch {
            try {
                createWaterSourceUseCase(
                    WaterSource(
                        -1,
                        selectedVolume.value!!,
                        selectWaterSourceType.value!!
                    )
                )
                emitDismissEvent()
            } catch (e: Exception) {
                logError("::onConfirmClick", e)
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
        view?.showVolumeInputDialog()
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

    private fun loadData() {
        viewModelScope.launch {
            try {
                with(getDefaultAddWaterSourceInfoUseCase()) {
                    selectedVolume.value = volume
                    selectWaterSourceType.value = waterSourceType
                }
            } catch (e: Exception) {
                logError("::loadData", e)
            }
        }
    }
}
