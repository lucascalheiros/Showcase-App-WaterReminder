package com.github.lucascalheiros.waterreminder.feature.history.ui.adddrinkentry

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterreminder.common.appcore.savedstatehandleproperty.SavedStateHandlePropertyKSerializable.Companion.savedStateKSerializableProperty
import com.github.lucascalheiros.waterreminder.common.util.logError
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDefaultAddWaterSourceInfoUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetWaterSourceTypeUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.RegisterConsumedWaterUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetVolumeUnitUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

class AddDrinkEntryPresenter(
    coroutineDispatcher: CoroutineDispatcher,
    state: SavedStateHandle,
    private val getWaterSourceTypeUseCase: GetWaterSourceTypeUseCase,
    private val getDefaultAddWaterSourceInfoUseCase: GetDefaultAddWaterSourceInfoUseCase,
    private val getVolumeUnitUseCase: GetVolumeUnitUseCase,
    private val registerConsumedWaterUseCase: RegisterConsumedWaterUseCase
) : BasePresenter<AddDrinkEntryContract.View>(coroutineDispatcher),
    AddDrinkEntryContract.Presenter {

    private val selectedVolumeProperty =
        state.savedStateKSerializableProperty<MeasureSystemVolume>(SELECTED_VOLUME_KEY, null)
    private val selectWaterSourceTypeProperty =
        state.savedStateKSerializableProperty<WaterSourceType>(SELECTED_WATER_SOURCE_TYPE_KEY, null)
    private val selectedDateTimeProperty =
        state.savedStateKSerializableProperty<LocalDateTime>(SELECTED_DATETIME_KEY, Clock.System.now().toLocalDateTime(
            TimeZone.currentSystemDefault()))

    private val dismissEvent = MutableStateFlow<Unit?>(null)
    private val errorEvent = MutableStateFlow<AddDrinkEntryContract.ErrorEvent?>(null)
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
                registerConsumedWaterUseCase(
                    selectedVolumeProperty.value!!,
                    selectWaterSourceTypeProperty.value!!,
                    selectedDateTimeProperty.value!!.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
                )
            } catch (e: Exception) {
                logError("::onConfirmClick", e)
                emitErrorEvent(AddDrinkEntryContract.ErrorEvent.SaveFailed)
            } finally {
                emitDismissEvent()
            }
        }
    }

    override fun onWaterSourceTypeSelected(waterSourceType: WaterSourceType) {
        selectWaterSourceTypeProperty.set(waterSourceType)
    }

    override fun onVolumeSelected(volumeValue: Double) {
        selectedVolumeProperty.set(
            selectedVolumeProperty.value?.volumeUnit()?.let { MeasureSystemVolume.create(volumeValue, it) }
        )
    }

    override fun onVolumeOptionClick() {
        viewModelScope.launch {
            try {
                val unit = getVolumeUnitUseCase.single()
                view?.showVolumeInputDialog(unit)
            } catch (e: Exception) {
                logError("::onVolumeOptionClick", e)
            }
        }
    }

    override fun onSelectWaterSourceTypeOptionClick() {
        viewModelScope.launch {
            try {
                view?.showSelectWaterSourceDialog(getWaterSourceTypeUseCase.single())
            } catch (e: Exception) {
                logError("::onSelectWaterSourceTypeOptionClick", e)
            }
        }
    }

    override fun onDateTimeClick() {
        view?.showDateTimeInputDialog(selectedDateTimeProperty.value ?: return)
    }

    override fun onDateTimeChange(localDateTime: LocalDateTime) {
        selectedDateTimeProperty.set(localDateTime)
    }

    override fun CoroutineScope.scopedViewUpdate() {
        collectErrorEvent()
        collectSelectedVolume()
        collectSelectedWaterSourceType()
        collectDismissEvent()
        collectSelectedDateTime()
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

    private fun CoroutineScope.collectSelectedDateTime() = launch {
        selectedDateTimeProperty.flow.filterNotNull().collectLatest {
            view?.setSelectedDateTime(it)
        }
    }

    private fun CoroutineScope.collectSelectedVolume() = launch {
        selectedVolumeProperty.flow.filterNotNull().collectLatest {
            view?.setSelectedVolume(it)
        }
    }

    private fun CoroutineScope.collectSelectedWaterSourceType() = launch {
        selectWaterSourceTypeProperty.flow.filterNotNull().collectLatest {
            view?.setSelectedWaterSourceType(it)
        }
    }

    private fun emitDismissEvent() {
        dismissEvent.value = Unit
    }

    private fun handleDismissEvent() {
        dismissEvent.value = null
    }

    private fun emitErrorEvent(event: AddDrinkEntryContract.ErrorEvent) {
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
                emitErrorEvent(AddDrinkEntryContract.ErrorEvent.DataLoadingFailed)
                emitDismissEvent()
            }
        }
    }

    companion object {
        private const val SELECTED_DATETIME_KEY = "SELECTED_DATETIME_KEY"
        private const val SELECTED_VOLUME_KEY = "SELECTED_VOLUME_KEY"
        private const val SELECTED_WATER_SOURCE_TYPE_KEY = "SELECTED_WATER_SOURCE_TYPE_KEY"
    }
}
