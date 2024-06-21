package com.github.lucascalheiros.waterreminder.feature.settings.ui.unitselector

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterreminder.common.util.logError
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemTemperatureUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolumeUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeightUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetTemperatureUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetVolumeUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetWeightUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.SetTemperatureUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.SetVolumeUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.SetWeightUnitUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class UnitSelectorPresenter(
    mainDispatcher: CoroutineDispatcher,
    private val getVolumeUnitUseCase: GetVolumeUnitUseCase,
    private val getWeightUnitUseCase: GetWeightUnitUseCase,
    private val getTemperatureUnitUseCase: GetTemperatureUnitUseCase,
    private val setVolumeUnitUseCase: SetVolumeUnitUseCase,
    private val setWeightUnitUseCase: SetWeightUnitUseCase,
    private val setTemperatureUnitUseCase: SetTemperatureUnitUseCase,
) : BasePresenter<UnitSelectorContract.View>(mainDispatcher),
    UnitSelectorContract.Presenter {

    private val volumeUnit = MutableStateFlow<MeasureSystemVolumeUnit?>(null)
    private val temperatureUnit = MutableStateFlow<MeasureSystemTemperatureUnit?>(null)
    private val weightUnit = MutableStateFlow<MeasureSystemWeightUnit?>(null)
    override fun loadData() {
        viewModelScope.launch {
            try {
                volumeUnit.value = getVolumeUnitUseCase.single()
                weightUnit.value = getWeightUnitUseCase.single()
                temperatureUnit.value = getTemperatureUnitUseCase.single()
            } catch (e: Exception) {
                logError("::loadData", e)
            }
        }
    }

    override fun onChangeVolumeUnit(value: MeasureSystemVolumeUnit) {
        val oldValue = volumeUnit.value
        if (oldValue == value) {
            return
        }
        viewModelScope.launch {
            try {
                volumeUnit.value = value
                setVolumeUnitUseCase(value)
            } catch (e: Exception) {
                logError("::onChangeVolumeUnit", e)
                volumeUnit.value = oldValue
            }
        }
    }

    override fun onChangeTemperatureUnit(value: MeasureSystemTemperatureUnit) {
        val oldValue = temperatureUnit.value
        if (oldValue == value) {
            return
        }
        viewModelScope.launch {
            try {
                temperatureUnit.value = value
                setTemperatureUnitUseCase(value)
            } catch (e: Exception) {
                logError("::onChangeVolumeUnit", e)
                temperatureUnit.value = oldValue
            }
        }
    }

    override fun onChangeWeightUnit(value: MeasureSystemWeightUnit) {
        val oldValue = weightUnit.value
        if (oldValue == value) {
            return
        }
        viewModelScope.launch {
            try {
                weightUnit.value = value
                setWeightUnitUseCase(value)
            } catch (e: Exception) {
                logError("::onChangeVolumeUnit", e)
                weightUnit.value = oldValue
            }
        }
    }

    override fun CoroutineScope.scopedViewUpdate() {
        launch {
            volumeUnit.filterNotNull().collectLatest {
                view?.setVolumeUnit(it)
            }
        }
        launch {
            temperatureUnit.filterNotNull().collectLatest {
                view?.setTemperatureUnit(it)
            }
        }
        launch {
            weightUnit.filterNotNull().collectLatest {
                view?.setWeightUnit(it)
            }
        }
    }
}