package com.github.lucascalheiros.waterreminder.feature.home.ui.drinkshortcut

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterreminder.common.appcore.savedstatehandleproperty.SavedStateHandlePropertyKSerializable.Companion.savedStateKSerializableProperty
import com.github.lucascalheiros.waterreminder.common.util.logError
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DefaultVolumeShortcuts
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDefaultVolumeShortcutsUseCase
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

class DrinkShortcutPresenter(
    coroutineDispatcher: CoroutineDispatcher,
    state: SavedStateHandle,
    private val getWaterSourceTypeUseCase: GetWaterSourceTypeUseCase,
    private val getVolumeUnitUseCase: GetVolumeUnitUseCase,
    private val registerConsumedWaterUseCase: RegisterConsumedWaterUseCase,
    private val getDefaultVolumeShortcutsUseCase: GetDefaultVolumeShortcutsUseCase,
) : BasePresenter<DrinkShortcutContract.View>(coroutineDispatcher),
    DrinkShortcutContract.Presenter {

    private val selectedVolumeProperty =
        state.savedStateKSerializableProperty<MeasureSystemVolume>(SELECTED_VOLUME_KEY, null)
    private val selectWaterSourceTypeProperty =
        state.savedStateKSerializableProperty<WaterSourceType>(SELECTED_WATER_SOURCE_TYPE_KEY, null)
    private val dismissEvent = MutableStateFlow<Unit?>(null)
    private val errorEvent = MutableStateFlow<DrinkShortcutContract.ErrorEvent?>(null)
    private val defaultVolumeShortcuts = MutableStateFlow<DefaultVolumeShortcuts?>(null)

    override fun initialize(waterSourceTypeId: Long) {
        viewModelScope.launch {
            try {
                val defaultVolumeShortcuts = getDefaultVolumeShortcutsUseCase().also {
                    defaultVolumeShortcuts.value = it
                }
                selectWaterSourceTypeProperty.set(getWaterSourceTypeUseCase(waterSourceTypeId))
                selectedVolumeProperty.set(defaultVolumeShortcuts.medium)
            } catch (e: Exception) {
                logError("::initialize", e)
            }
        }
    }

    override fun onCancelClick() {
        emitDismissEvent()
    }

    override fun onConfirmClick() {
        viewModelScope.launch {
            try {
                registerConsumedWaterUseCase(
                    selectedVolumeProperty.value!!,
                    selectWaterSourceTypeProperty.value!!
                )
            } catch (e: Exception) {
                logError("::onConfirmClick", e)
                emitErrorEvent(DrinkShortcutContract.ErrorEvent.SaveFailed)
            } finally {
                emitDismissEvent()
            }
        }
    }

    override fun onVolumeSelected(volumeValue: Double) {
        selectedVolumeProperty.set(
            selectedVolumeProperty.value?.volumeUnit()
                ?.let { MeasureSystemVolume.create(volumeValue, it) }
        )
    }

    override fun CoroutineScope.scopedViewUpdate() {
        collectErrorEvent()
        collectSelectedVolume()
        collectDismissEvent()
        collectVolumeShortcutDefaults()
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
        selectedVolumeProperty.flow.filterNotNull().collectLatest {
            view?.setSelectedVolume(it)
        }
    }

    private fun CoroutineScope.collectVolumeShortcutDefaults() = launch {
        defaultVolumeShortcuts.filterNotNull().collectLatest {
            view?.setVolumeShortcuts(it)
        }
    }

    private fun emitDismissEvent() {
        dismissEvent.value = Unit
    }

    private fun handleDismissEvent() {
        dismissEvent.value = null
    }

    private fun emitErrorEvent(event: DrinkShortcutContract.ErrorEvent) {
        errorEvent.value = event
    }

    private fun handleErrorEvent() {
        errorEvent.value = null
    }

    companion object {
        private const val SELECTED_VOLUME_KEY = "SELECTED_VOLUME_KEY"
        private const val SELECTED_WATER_SOURCE_TYPE_KEY = "SELECTED_WATER_SOURCE_TYPE_KEY"
    }
}