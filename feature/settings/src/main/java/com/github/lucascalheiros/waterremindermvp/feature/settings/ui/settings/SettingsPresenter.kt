package com.github.lucascalheiros.waterremindermvp.feature.settings.ui.settings

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterremindermvp.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemUnit
import com.github.lucascalheiros.waterremindermvp.common.measuresystem.MeasureSystemVolume
import com.github.lucascalheiros.waterremindermvp.common.util.logError
import com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.models.AppTheme
import com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.usecases.GetThemeUseCase
import com.github.lucascalheiros.waterremindermvp.domain.userinformation.domain.usecases.SetThemeUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.GetCurrentMeasureSystemUnitUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.GetDailyWaterConsumptionUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.RegisterCurrentMeasureSystemUnitUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.SaveDailyWaterConsumptionUseCase
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.requests.AsyncRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class SettingsPresenter(
    mainDispatcher: CoroutineDispatcher,
    getDailyWaterConsumptionUseCase: GetDailyWaterConsumptionUseCase,
    private val getCurrentMeasureSystemUnitUseCase: GetCurrentMeasureSystemUnitUseCase,
    getThemeUseCase: GetThemeUseCase,
    private val setThemeUseCase: SetThemeUseCase,
    private val registerCurrentMeasureSystemUnitUseCase: RegisterCurrentMeasureSystemUnitUseCase,
    private val saveDailyWaterConsumptionUseCase: SaveDailyWaterConsumptionUseCase,
) : BasePresenter<SettingsContract.View>(mainDispatcher),
    SettingsContract.Presenter {

    private val dailyWaterIntake = getDailyWaterConsumptionUseCase(AsyncRequest.Continuous).filterNotNull()
    private val measureSystemUnit = getCurrentMeasureSystemUnitUseCase(AsyncRequest.Continuous)
    private val theme by lazy { getThemeUseCase() }

    override fun onDailyWaterIntakeOptionClick() {
        viewModelScope.launch {
            try {
                val unit = getCurrentMeasureSystemUnitUseCase(AsyncRequest.Single).toVolumeUnit()
                view?.showDailyWaterIntakeInputDialog(unit)
            } catch (e: Exception) {
                logError("::onDailyWaterIntakeOptionClick", e)
            }
        }
    }

    override fun onDailyWaterIntakeChanged(volumeValue: Double) {
        viewModelScope.launch {
            try {
                val unit = getCurrentMeasureSystemUnitUseCase(AsyncRequest.Single)
                saveDailyWaterConsumptionUseCase(
                    MeasureSystemVolume.Companion.create(
                        volumeValue,
                        unit
                    )
                )
            } catch (e: Exception) {
                logError("::onDailyWaterIntakeChanged", e)
            }
        }
    }

    override fun onMeasureSystemSelected(unit: MeasureSystemUnit) {
        viewModelScope.launch {
            try {
                registerCurrentMeasureSystemUnitUseCase(unit)
            } catch (e: Exception) {
                logError("::onMeasureSystemSelected", e)
            }
        }
    }

    override fun onThemeSelected(theme: AppTheme) {
        viewModelScope.launch {
            try {
                setThemeUseCase(theme)
            } catch (e: Exception) {
                logError("::onThemeSelected", e)
            }
        }
    }

    override fun onNotificationEnableChanged(state: Boolean) {
    }

    override fun CoroutineScope.scopedViewUpdate() {
        launch {
            dailyWaterIntake.collectLatest {
                view?.setDailyWaterIntake(it.expectedVolume)
            }
        }
        launch {
            measureSystemUnit.collectLatest {
                view?.setMeasureSystemUnit(it)
            }
        }
        launch {
            theme.collectLatest {
                view?.setTheme(it)
            }
        }
    }
}