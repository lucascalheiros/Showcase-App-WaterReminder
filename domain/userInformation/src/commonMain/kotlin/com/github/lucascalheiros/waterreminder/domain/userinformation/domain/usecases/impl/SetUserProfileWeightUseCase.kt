package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.repositories.UserProfileRepository
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetUserProfileWeightUseCase
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemWeight
import kotlinx.coroutines.flow.first

class SetUserProfileWeightUseCaseImpl(
    private val userProfileRepository: UserProfileRepository
) : SetUserProfileWeightUseCase {
    override suspend fun invoke(weight: MeasureSystemWeight) {
        userProfileRepository.getUserProfile().first().copy(
            weight = weight
        ).also {
            userProfileRepository.setUserProfile(it)
        }
    }
}