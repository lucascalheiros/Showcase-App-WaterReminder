package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.activitylevelinput

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.ActivityLevel
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.GetUserProfileUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetUserProfileActivityLevelUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ActivityLevelInputPresenter(
    mainDispatcher: CoroutineDispatcher,
    getUserProfileUseCase: GetUserProfileUseCase,
    private val setUserProfileActivityLevelUseCase: SetUserProfileActivityLevelUseCase
) : BasePresenter<ActivityLevelInputContract.View>(mainDispatcher),
    ActivityLevelInputContract.Presenter {

    private val selectableCards = getUserProfileUseCase().map { it.activityLevel }

    override fun selectCard(activityLevel: ActivityLevel) {
        viewModelScope.launch {
            setUserProfileActivityLevelUseCase(activityLevel)
        }
    }

    override fun CoroutineScope.scopedViewUpdate() {
        launch {
            selectableCards.collectLatest {
                view?.updateSelected(it)
            }
        }
    }
}
