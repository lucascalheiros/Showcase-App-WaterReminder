package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.repositories.UserProfileRepository
import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases.SetUserProfileNameUseCase
import kotlinx.coroutines.flow.first

class SetUserProfileNameUseCaseImpl(
    private val userProfileRepository: UserProfileRepository
) : SetUserProfileNameUseCase {
    override suspend fun invoke(name: String) {
        userProfileRepository.getUserProfile().first().copy(
            name = name
        ).also {
            userProfileRepository.setUserProfile(it)
        }
    }
}