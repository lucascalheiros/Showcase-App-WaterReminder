package com.github.lucascalheiros.waterreminder.feature.home.ui.adddrink

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterreminder.common.util.logError
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ThemeAwareColor
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.CreateWaterSourceTypeUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.GetDefaultAddDrinkInfoUseCase
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.CreateWaterSourceTypeRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class AddDrinkPresenter(
    coroutineDispatcher: CoroutineDispatcher,
    private val createWaterSourceTypeUseCase: CreateWaterSourceTypeUseCase,
    private val getDefaultAddDrinkInfoUseCase: GetDefaultAddDrinkInfoUseCase
) : BasePresenter<AddDrinkContract.View>(coroutineDispatcher),
    AddDrinkContract.Presenter {

    private val dismissEvent = MutableStateFlow<Unit?>(null)
    private val name = MutableStateFlow("")
    private val hydrationFactor = MutableStateFlow<Float?>(null)
    private val themeAwareColor = MutableStateFlow<ThemeAwareColor?>(null)
    private val isConfirmEnabled =
        combine(name, hydrationFactor, themeAwareColor) { name, hydrationFactor, themeAwareColor ->
            name.isNotBlank() && hydrationFactor?.let { it > 0.0 } != null && themeAwareColor != null
        }

    override fun initialize() {
        viewModelScope.launch {
            val defaultInfo = getDefaultAddDrinkInfoUseCase()
            hydrationFactor.value = defaultInfo.hydrationFactor
            themeAwareColor.value = defaultInfo.themeAwareColor
        }
    }

    override fun onCancelClick() {
        emitDismissEvent()
    }

    override fun onConfirmClick() {
        viewModelScope.launch {
            try {
                createWaterSourceTypeUseCase(
                    CreateWaterSourceTypeRequest(
                        name.value,
                        themeAwareColor.value!!,
                        hydrationFactor.value!!
                    )
                )
                emitDismissEvent()
            } catch (e: Exception) {
                logError("::onConfirmClick", e)
            }
        }
    }

    override fun onNameChange(value: String) {
        name.value = value.trim()
    }

    override fun onHydrationClick() {
        view?.showHydrationFactorDialog(hydrationFactor.value ?: 1f)
    }

    override fun onHydrationChange(value: Float) {
        hydrationFactor.value = value
    }

    override fun onColorClick() {
        view?.showColorSelectorDialog(themeAwareColor.value ?: return)
    }

    override fun onColorChange(value: ThemeAwareColor) {
        themeAwareColor.value = value
    }

    override fun CoroutineScope.scopedViewUpdate() {
        collectDismissEvent()
        launch {
            name.filterNotNull().collectLatest {
                view?.setName(it)
            }
        }
        launch {
            hydrationFactor.filterNotNull().collectLatest {
                view?.setHydration(it)
            }
        }
        launch {
            themeAwareColor.filterNotNull().collectLatest {
                view?.setColor(it)
            }
        }
        launch {
            isConfirmEnabled.collectLatest {
                view?.setConfirmState(it)
            }
        }
    }

    private fun CoroutineScope.collectDismissEvent() = launch {
        dismissEvent.filterNotNull().collectLatest {
            view?.run {
                dismissBottomSheet()
                handleDismissEvent()
            }
        }
    }

    private fun emitDismissEvent() {
        dismissEvent.value = Unit
    }

    private fun handleDismissEvent() {
        dismissEvent.value = null
    }
}
