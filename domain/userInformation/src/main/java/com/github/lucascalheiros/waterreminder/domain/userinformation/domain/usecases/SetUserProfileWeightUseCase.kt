package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeight

interface SetUserProfileWeightUseCase {
    suspend operator fun invoke(weight: MeasureSystemWeight)
}