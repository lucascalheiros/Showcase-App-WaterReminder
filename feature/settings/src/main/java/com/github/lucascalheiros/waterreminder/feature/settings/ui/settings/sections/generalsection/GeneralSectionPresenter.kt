package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.sections.generalsection

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterreminder.common.util.logError
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AppTheme
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.GetThemeUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetThemeUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDailyWaterConsumptionUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.SaveDailyWaterConsumptionUseCase
import com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.sections.generalsection.models.SettingUnits
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetTemperatureUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetVolumeUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetWeightUnitUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class GeneralSectionPresenter(
    mainDispatcher: CoroutineDispatcher,
    getDailyWaterConsumptionUseCase: GetDailyWaterConsumptionUseCase,
    getThemeUseCase: GetThemeUseCase,
    private val getVolumeUnitUseCase: GetVolumeUnitUseCase,
    getWeightUnitUseCase: GetWeightUnitUseCase,
    getTemperatureUnitUseCase: GetTemperatureUnitUseCase,
    private val setThemeUseCase: SetThemeUseCase,
    private val saveDailyWaterConsumptionUseCase: SaveDailyWaterConsumptionUseCase,
) : BasePresenter<GeneralSectionContract.View>(mainDispatcher),
    GeneralSectionContract.Presenter {

    private val dailyWaterIntake = getDailyWaterConsumptionUseCase().filterNotNull()
    private val units = combine(
        getVolumeUnitUseCase(),
        getWeightUnitUseCase(),
        getTemperatureUnitUseCase()
    ) { volume, weight, temperature ->
        SettingUnits(volume, temperature, weight)
    }

    private val theme by lazy { getThemeUseCase() }

    override fun onDailyWaterIntakeOptionClick() {
        viewModelScope.launch {
            try {
                val unit = getVolumeUnitUseCase.single()
                view?.showDailyWaterIntakeInputDialog(unit)
            } catch (e: Exception) {
                logError("::onDailyWaterIntakeOptionClick", e)
            }
        }
    }

    override fun onDailyWaterIntakeChanged(volumeValue: Double) {
        viewModelScope.launch {
            try {
                val unit = getVolumeUnitUseCase.single()
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

    override fun onThemeSelected(theme: AppTheme) {
        viewModelScope.launch {
            try {
                setThemeUseCase(theme)
            } catch (e: Exception) {
                logError("::onThemeSelected", e)
            }
        }
    }


    override fun CoroutineScope.scopedViewUpdate() {
        launch {
            dailyWaterIntake.collectLatest {
                view?.setDailyWaterIntake(it.expectedVolume)
            }
        }
        launch {
            units.collectLatest {
                view?.setUnits(it)
            }
        }
        launch {
            theme.collectLatest {
                view?.setTheme(it)
            }
        }
    }
}