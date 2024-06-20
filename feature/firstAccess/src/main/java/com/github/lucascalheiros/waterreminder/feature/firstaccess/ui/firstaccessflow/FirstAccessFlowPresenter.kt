package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.firstaccessflow

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterreminder.common.util.logError
import com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.usecases.CompleteFirstAccessFlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class FirstAccessFlowPresenter(
    mainDispatcher: CoroutineDispatcher,
    private val completeFirstAccessFlowUseCase: CompleteFirstAccessFlowUseCase
) : BasePresenter<FirstAccessFlowContract.View>(mainDispatcher),
    FirstAccessFlowContract.Presenter {

    private val navigateToMainFlow = MutableStateFlow<Unit?>(null)

    override fun onConfirmFirstAccessFlow() {
        viewModelScope.launch {
            try {
                completeFirstAccessFlowUseCase()
                navigateToMainFlow.value = Unit
            } catch (e: Exception) {
                logError("::setVolumeUnit", e)
            }
        }
    }

    override fun CoroutineScope.scopedViewUpdate() {
        launch {
            navigateToMainFlow.filterNotNull().collectLatest {
                view?.navigateToMainFlow() ?: return@collectLatest
                handleNavigationToMainFlow()
            }
        }
    }

    private fun handleNavigationToMainFlow() {
        navigateToMainFlow.value = null
    }

}