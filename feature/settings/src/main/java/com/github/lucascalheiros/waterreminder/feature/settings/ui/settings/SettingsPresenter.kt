package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterreminder.common.util.logError
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.IsNotificationsEnabledUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.SetNotificationsEnabledUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AmbienceTemperatureLevel
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AppTheme
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.GetCalculatedIntakeUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.GetThemeUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.GetUserProfileUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetThemeUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetUserProfileActivityLevelUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetUserProfileNameUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetUserProfileTemperatureLevelUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetUserProfileWeightUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDailyWaterConsumptionUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.SaveDailyWaterConsumptionUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeight
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetCurrentMeasureSystemUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.RegisterCurrentMeasureSystemUnitUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class SettingsPresenter(
    mainDispatcher: CoroutineDispatcher,
    getDailyWaterConsumptionUseCase: GetDailyWaterConsumptionUseCase,
    getUserProfileUseCase: GetUserProfileUseCase,
    getCalculatedIntakeUseCase: GetCalculatedIntakeUseCase,
    isNotificationsEnabledUseCase: IsNotificationsEnabledUseCase,
    getThemeUseCase: GetThemeUseCase,
    private val getCurrentMeasureSystemUnitUseCase: GetCurrentMeasureSystemUnitUseCase,
    private val setThemeUseCase: SetThemeUseCase,
    private val registerCurrentMeasureSystemUnitUseCase: RegisterCurrentMeasureSystemUnitUseCase,
    private val saveDailyWaterConsumptionUseCase: SaveDailyWaterConsumptionUseCase,
    private val setNotificationsEnabledUseCase: SetNotificationsEnabledUseCase,
    private val setUserProfileNameUseCase: SetUserProfileNameUseCase,
    private val setUserProfileWeightUseCase: SetUserProfileWeightUseCase,
    private val setUserProfileActivityLevelUseCase: SetUserProfileActivityLevelUseCase,
    private val setUserProfileTemperatureLevelUseCase: SetUserProfileTemperatureLevelUseCase,
) : BasePresenter<SettingsContract.View>(mainDispatcher),
    SettingsContract.Presenter {

    private val dailyWaterIntake = getDailyWaterConsumptionUseCase().filterNotNull()
    private val measureSystemUnit = getCurrentMeasureSystemUnitUseCase()
    private val theme by lazy { getThemeUseCase() }
    private val isNotificationEnabled = isNotificationsEnabledUseCase()
    private val userProfile = getUserProfileUseCase()
    private val calculatedInTake = getCalculatedIntakeUseCase()

    override fun onDailyWaterIntakeOptionClick() {
        viewModelScope.launch {
            try {
                val unit = getCurrentMeasureSystemUnitUseCase.single().toVolumeUnit()
                view?.showDailyWaterIntakeInputDialog(unit)
            } catch (e: Exception) {
                logError("::onDailyWaterIntakeOptionClick", e)
            }
        }
    }

    override fun onDailyWaterIntakeChanged(volumeValue: Double) {
        viewModelScope.launch {
            try {
                val unit = getCurrentMeasureSystemUnitUseCase.single()
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
        viewModelScope.launch {
            try {
                setNotificationsEnabledUseCase(state)
            } catch (e: Exception) {
                logError("::onNotificationEnableChanged", e)
            }
        }
    }

    override fun onManageNotificationsClick() {
        view?.openManageNotifications()
    }


    override fun onUserNameSet(name: String) {
        viewModelScope.launch {
            try {
                setUserProfileNameUseCase(name)
            } catch (e: Exception) {
                logError("::onUserNameSet", e)
            }
        }
    }

    override fun onUserWeightSet(weight: MeasureSystemWeight) {
        viewModelScope.launch {
            try {
                setUserProfileWeightUseCase(weight)
            } catch (e: Exception) {
                logError("::onUserNameSet", e)
            }
        }
    }

    override fun onUserActivityLevelSet(activityLevel: Int) {
        viewModelScope.launch {
            try {
                setUserProfileActivityLevelUseCase(activityLevel)
            } catch (e: Exception) {
                logError("::onUserNameSet", e)
            }
        }
    }

    override fun onUserTemperatureLevelSet(temperatureLevel: AmbienceTemperatureLevel) {
        viewModelScope.launch {
            try {
                setUserProfileTemperatureLevelUseCase(temperatureLevel)
            } catch (e: Exception) {
                logError("::onUserNameSet", e)
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
            measureSystemUnit.collectLatest {
                view?.setMeasureSystemUnit(it)
            }
        }
        launch {
            theme.collectLatest {
                view?.setTheme(it)
            }
        }
        launch {
            isNotificationEnabled.collectLatest {
                view?.setNotificationEnabledState(it)
            }
        }
        launch {
            userProfile.collectLatest {
                view?.setUserProfile(it)
            }
        }
        launch {
            calculatedInTake.collectLatest {
                view?.setCalculatedIntake(it)
            }
        }
    }
}