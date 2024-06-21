package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.firstaccessflow

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterreminder.common.util.logError
import com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.usecases.CompleteFirstAccessFlowUseCase
import com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.usecases.GetFirstAccessNotificationDataUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FirstAccessFlowPresenter(
    mainDispatcher: CoroutineDispatcher,
    private val completeFirstAccessFlowUseCase: CompleteFirstAccessFlowUseCase,
    private val getFirstAccessNotificationDataUseCase: GetFirstAccessNotificationDataUseCase
) : BasePresenter<FirstAccessFlowContract.View>(mainDispatcher),
    FirstAccessFlowContract.Presenter {

    private val onConfirmEvents = MutableStateFlow<OnConfirmEvents?>(null)

    override fun onPermissionsHandled() {
        viewModelScope.launch {
            try {
                completeFlow()
            } catch (e: Exception) {
                logError("::onExactSchedulePermissionRequestDismiss", e)
                onConfirmEvents.value = OnConfirmEvents.ConfirmationError
            }
        }
    }

    override fun onConfirmFirstAccessFlow() {
        viewModelScope.launch {
            try {
                if (!getFirstAccessNotificationDataUseCase().first().shouldEnable) {
                    completeFlow()
                } else {
                    onConfirmEvents.value = OnConfirmEvents.RequestPermissions
                }
            } catch (e: Exception) {
                logError("::onConfirmFirstAccessFlow", e)
                onConfirmEvents.value = OnConfirmEvents.ConfirmationError
            }
        }
    }

    override fun CoroutineScope.scopedViewUpdate() {
        launch {
            onConfirmEvents.filterNotNull().collectLatest {
                val view = view ?: return@collectLatest
                handleConfirmationEventState()
                when (it) {
                    OnConfirmEvents.RequestPermissions -> view.requestPermissions()
                    OnConfirmEvents.CompleteFlow -> view.navigateToMainFlow()
                    OnConfirmEvents.ConfirmationError -> view.showConfirmationFailureToast()
                }
            }
        }
    }

    private suspend fun completeFlow() {
        completeFirstAccessFlowUseCase()
        onConfirmEvents.value = OnConfirmEvents.CompleteFlow
    }

    private fun handleConfirmationEventState() {
        onConfirmEvents.value = null
    }

    private enum class OnConfirmEvents {
        RequestPermissions,
        CompleteFlow,
        ConfirmationError
    }
}