package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.confirmationinput

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterreminder.common.util.logError
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDailyWaterConsumptionUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.SaveDailyWaterConsumptionUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetVolumeUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.SetVolumeUnitUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.SetFirstDailyIntakeUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ConfirmationInputPresenter(
    mainDispatcher: CoroutineDispatcher,
    getDailyWaterConsumptionUseCase: GetDailyWaterConsumptionUseCase,
    private val saveDailyWaterConsumptionUseCase: SaveDailyWaterConsumptionUseCase,
    private val setFirstDailyIntakeUseCase: SetFirstDailyIntakeUseCase,
    private val setVolumeUnitUseCase: SetVolumeUnitUseCase,
    private val getVolumeUnitUseCase: GetVolumeUnitUseCase,
) : BasePresenter<ConfirmationInputContract.View>(mainDispatcher),
    ConfirmationInputContract.Presenter {

    private val volume = getDailyWaterConsumptionUseCase.invoke().filterNotNull().map { it.expectedVolume }
    private val volumeUnit = volume.map { it.volumeUnit() }
    private val uiEvents = MutableStateFlow<UiEvents?>(null)

    override fun loadData() {
        viewModelScope.launch {
            try {
                setFirstDailyIntakeUseCase()
            } catch (e: Exception) {
                logError("::loadData", e)
            }
        }
    }

    override fun onVolumeClick() {
        viewModelScope.launch {
            val unit = getVolumeUnitUseCase.single()
            uiEvents.value = UiEvents.ShowVolumeInputDialog(unit)
        }
    }

    override fun onVolumeUnitSelected(volumeUnit: MeasureSystemVolumeUnit) {
        viewModelScope.launch {
            try {
                setVolumeUnitUseCase(volumeUnit)
            } catch (e: Exception) {
                logError("::setVolumeUnit", e)
            }
        }
    }

    override fun onVolumeSelected(volumeInMl: Double) {
        viewModelScope.launch {
            try {
                saveDailyWaterConsumptionUseCase(MeasureSystemVolume.create(volumeInMl, MeasureSystemVolumeUnit.ML))
            } catch (e: Exception) {
                logError("::setVolumeInMl", e)
            }
        }
    }

    override fun CoroutineScope.scopedViewUpdate() {
        launch {
            volumeUnit.collectLatest {
                view?.updateVolumeUnit(it)
            }
        }
        launch {
            volume.collectLatest {
                view?.updateVolume(it)
            }
        }
        launch {
            uiEvents.filterNotNull().collectLatest {
                val view = view ?: return@collectLatest
                when (it) {
                    is UiEvents.ShowVolumeInputDialog -> view.showVolumeInputDialog(it.unit)
                }
                handleUiEvent()
            }
        }
    }

    private fun handleUiEvent() {
        uiEvents.value = null
    }
}

private sealed interface UiEvents {
    data class ShowVolumeInputDialog(val unit: MeasureSystemVolumeUnit): UiEvents
}