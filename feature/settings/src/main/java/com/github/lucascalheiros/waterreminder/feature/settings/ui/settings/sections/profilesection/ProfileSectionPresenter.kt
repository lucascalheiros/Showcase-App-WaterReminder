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
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeight
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileSectionPresenter(
    mainDispatcher: CoroutineDispatcher,
    getUserProfileUseCase: GetUserProfileUseCase,
    getCalculatedIntakeUseCase: GetCalculatedIntakeUseCase,
    private val setUserProfileNameUseCase: SetUserProfileNameUseCase,
    private val setUserProfileWeightUseCase: SetUserProfileWeightUseCase,
    private val setUserProfileActivityLevelUseCase: SetUserProfileActivityLevelUseCase,
    private val setUserProfileTemperatureLevelUseCase: SetUserProfileTemperatureLevelUseCase,
) : BasePresenter<ProfileSectionContract.View>(mainDispatcher),
    ProfileSectionContract.Presenter {

    private val userProfile = getUserProfileUseCase()
    private val calculatedInTake = getCalculatedIntakeUseCase()

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

    override fun onUserActivityLevelSet(activityLevel: ActivityLevel) {
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