package com.github.lucascalheiros.waterreminder.feature.settings.ui.settings.sections.remindnotificationsection

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterreminder.common.util.logError
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.IsNotificationsEnabledUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.SetNotificationsEnabledUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RemindNotificationSectionPresenter(
    mainDispatcher: CoroutineDispatcher,
    isNotificationsEnabledUseCase: IsNotificationsEnabledUseCase,
    private val setNotificationsEnabledUseCase: SetNotificationsEnabledUseCase,
) : BasePresenter<RemindNotificationSectionContract.View>(mainDispatcher),
    RemindNotificationSectionContract.Presenter {

    private val isNotificationEnabled = isNotificationsEnabledUseCase()
    private val hasPendingPermission = MutableStateFlow(false)

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

    override fun onNecessaryPermissionUpdate(hasPendingPermission: Boolean) {
        this.hasPendingPermission.value = hasPendingPermission
    }

    override fun CoroutineScope.scopedViewUpdate() {
        launch {
            isNotificationEnabled.collectLatest {
                view?.setNotificationSwitchState(it)
            }
        }
        launch {
            hasPendingPermission.collectLatest {
                view?.setPermissionRequiredOptionVisible(it)
                view?.setEnableStateOfNonPermissionOptions(!it)
            }
        }
    }
}