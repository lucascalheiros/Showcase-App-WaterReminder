package com.github.lucascalheiros.waterreminder.feature.firstaccess.ui.weightinput

import androidx.lifecycle.viewModelScope
import com.github.lucascalheiros.waterreminder.common.appcore.mvp.BasePresenter
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.GetUserProfileUseCase
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetUserProfileWeightUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeight
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeightUnit
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.GetWeightUnitUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.usecases.SetWeightUnitUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class WeightInputPresenter(
    mainDispatcher: CoroutineDispatcher,
    private val setUserProfileWeightUseCase: SetUserProfileWeightUseCase,
    getUserProfileUseCase: GetUserProfileUseCase,
    private val getWeightUnitUseCase: GetWeightUnitUseCase,
    private val setWeightUnitUseCase: SetWeightUnitUseCase,
) : BasePresenter<WeightInputContract.View>(mainDispatcher),
    WeightInputContract.Presenter {

    private val weight = getUserProfileUseCase().map { it.weight }

    private val selectedWeight = weight.map { it.intrinsicValue() }
    private val selectedWeightUnit = weight.map { it.weightUnit() }

    override fun setIntrinsicWeight(weight: Double) {
        viewModelScope.launch {
            val unit = getWeightUnitUseCase.single()
            setUserProfileWeightUseCase(MeasureSystemWeight.Companion.create(weight, unit))
        }
    }

    override fun setWeightUnit(weightUnit: MeasureSystemWeightUnit) {
        viewModelScope.launch {
            setWeightUnitUseCase(weightUnit)
        }

    }

    override fun CoroutineScope.scopedViewUpdate() {
        launch {
            selectedWeight.filterNotNull().collectLatest {
                view?.setIntrinsicWeight(it)
            }
        }
        launch {
            selectedWeightUnit.filterNotNull().collectLatest {
                view?.setWeightUnit(it)
            }
        }
    }
}