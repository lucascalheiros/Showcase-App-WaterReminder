package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.temperaturelevelinput

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AmbienceTemperatureLevel
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.GetUserProfileUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetUserProfileTemperatureLevelUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemTemperatureUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetTemperatureUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.SetTemperatureUnitUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TemperatureLevelInputPresenter(
    mainDispatcher: CoroutineDispatcher,
    getTemperatureUnitUseCase: GetTemperatureUnitUseCase,
    private val setTemperatureUnitUseCase: SetTemperatureUnitUseCase,
    getUserProfileUseCase: GetUserProfileUseCase,
    private val setUserProfileTemperatureLevelUseCase: SetUserProfileTemperatureLevelUseCase
) : BasePresenter<TemperatureLevelInputContract.View>(mainDispatcher),
    TemperatureLevelInputContract.Presenter {

    private val selectedTemperatureLevel = getUserProfileUseCase().map { it.temperatureLevel }
    private val temperatureUnit = getTemperatureUnitUseCase()

    override fun selectCard(temperatureLevel: AmbienceTemperatureLevel) {
        viewModelScope.launch {
            setUserProfileTemperatureLevelUseCase(temperatureLevel)
        }
    }

    override fun setTemperatureUnit(unit: MeasureSystemTemperatureUnit) {
        viewModelScope.launch {
            setTemperatureUnitUseCase(unit)
        }
    }

    override fun CoroutineScope.scopedViewUpdate() {
        launch {
            selectedTemperatureLevel.collectLatest {
                view?.updateSelected(it)
            }
        }
        launch {
            temperatureUnit.collectLatest {
                view?.updateTemperatureUnit(it)
            }
        }
    }
}
