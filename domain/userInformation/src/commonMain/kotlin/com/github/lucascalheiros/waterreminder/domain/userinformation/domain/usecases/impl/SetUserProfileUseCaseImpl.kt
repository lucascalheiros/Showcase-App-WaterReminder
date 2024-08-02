package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.UserProfile
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.repositories.UserProfileRepository
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetUserProfileUseCase

class SetUserProfileUseCaseImpl(
    private val userProfileRepository: UserProfileRepository
): SetUserProfileUseCase {
    override suspend fun invoke(userProfile: UserProfile) {
        userProfileRepository.setUserProfile(userProfile)
    }
}