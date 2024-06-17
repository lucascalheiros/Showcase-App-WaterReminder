package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.ActivityLevel
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.repositories.UserProfileRepository
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetUserProfileActivityLevelUseCase
import kotlinx.coroutines.flow.first

class SetUserProfileActivityLevelUseCaseImpl(
    private val userProfileRepository: UserProfileRepository
) : SetUserProfileActivityLevelUseCase {
    override suspend fun invoke(activityLevel: ActivityLevel) {
        userProfileRepository.getUserProfile().first().copy(
            activityLevel = activityLevel
        ).also {
            userProfileRepository.setUserProfile(it)
        }
    }
}