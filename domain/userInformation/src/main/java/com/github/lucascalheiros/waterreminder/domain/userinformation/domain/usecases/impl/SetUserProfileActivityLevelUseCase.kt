package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.repositories.UserProfileRepository
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetUserProfileActivityLevelUseCase
import kotlinx.coroutines.flow.first

class SetUserProfileActivityLevelUseCaseImpl(
    private val userProfileRepository: UserProfileRepository
) : SetUserProfileActivityLevelUseCase {
    override suspend fun invoke(activityWeekDays: Int) {
        userProfileRepository.getUserProfile().first().copy(
            activityLevelInWeekDays = activityWeekDays
        ).also {
            userProfileRepository.setUserProfile(it)
        }
    }
}