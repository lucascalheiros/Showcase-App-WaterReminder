package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.sections.profilesection

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterreminder.common.util.logError
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.ActivityLevel
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.AmbienceTemperatureLevel
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.GetCalculatedIntakeUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.GetUserProfileUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetUserProfileActivityLevelUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetUserProfileNameUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetUserProfileTemperatureLevelUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetUserProfileWeightUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.SaveDailyWaterConsumptionUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeight
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetWeightUnitUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileSectionPresenter(
    mainDispatcher: CoroutineDispatcher,
    getUserProfileUseCase: GetUserProfileUseCase,
    getCalculatedIntakeUseCase: GetCalculatedIntakeUseCase,
    private val setUserProfileNameUseCase: SetUserProfileNameUseCase,
    private val setUserProfileWeightUseCase: SetUserProfileWeightUseCase,
    private val setUserProfileActivityLevelUseCase: SetUserProfileActivityLevelUseCase,
    private val setUserProfileTemperatureLevelUseCase: SetUserProfileTemperatureLevelUseCase,
    private val getWeightUnitUseCase: GetWeightUnitUseCase,
    private val saveDailyWaterConsumptionUseCase: SaveDailyWaterConsumptionUseCase
) : BasePresenter<ProfileSectionContract.View>(mainDispatcher),
    ProfileSectionContract.Presenter {

    private val userProfile = getUserProfileUseCase()
    private val calculatedInTake = getCalculatedIntakeUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    override fun onUserNameSet(name: String) {
        viewModelScope.launch (CoroutineExceptionHandler { _, e ->
            logError("::onUserNameSet", e)
        }) {
            setUserProfileNameUseCase(name)
        }
    }

    override fun onUserWeightSet(weight: MeasureSystemWeight) {
        viewModelScope.launch (CoroutineExceptionHandler { _, e ->
            logError("::onUserWeightSet", e)
        }) {
            setUserProfileWeightUseCase(weight)
        }
    }

    override fun onUserActivityLevelSet(activityLevel: ActivityLevel) {
        viewModelScope.launch (CoroutineExceptionHandler { _, e ->
            logError("::onUserActivityLevelSet", e)
        }) {
            setUserProfileActivityLevelUseCase(activityLevel)
        }
    }

    override fun onUserTemperatureLevelSet(temperatureLevel: AmbienceTemperatureLevel) {
        viewModelScope.launch (CoroutineExceptionHandler { _, e ->
            logError("::onUserTemperatureLevelSet", e)
        }) {
            setUserProfileTemperatureLevelUseCase(temperatureLevel)
        }
    }

    override fun onShowWeightInputDialog() {
        viewModelScope.launch(CoroutineExceptionHandler { _, e ->
            logError("::onShowWeightInputDialog", e)
        }) {
            view?.showWeightInputDialog(getWeightUnitUseCase.single())
        }
    }

    override fun onSetCalculatedIntakeAsDailyIntake() {
        viewModelScope.launch(CoroutineExceptionHandler { _, e ->
            logError("::onSetCalculatedIntakeAsDailyIntake", e)
        }) {
            saveDailyWaterConsumptionUseCase(calculatedInTake.value!!)
        }
    }

    override fun CoroutineScope.scopedViewUpdate() {
        launch {
            userProfile.collectLatest {
                view?.setUserProfile(it)
            }
        }
        launch {
            calculatedInTake.filterNotNull().collectLatest {
                view?.setCalculatedIntake(it)
            }
        }
    }
}