package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.UserProfile
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.repositories.UserProfileRepository
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.GetUserProfileUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class GetUserProfileUseCaseImpl(
    private val userProfileRepository: UserProfileRepository
): GetUserProfileUseCase {
    override fun invoke(): Flow<UserProfile> {
        return userProfileRepository.getUserProfile()
    }

    override suspend fun single(): UserProfile {
        return userProfileRepository.getUserProfile().first()
    }
}