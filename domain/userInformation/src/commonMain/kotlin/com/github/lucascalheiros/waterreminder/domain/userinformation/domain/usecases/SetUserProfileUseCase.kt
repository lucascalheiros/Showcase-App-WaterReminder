package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.UserProfile

interface SetUserProfileUseCase {
    suspend operator fun invoke(userProfile: UserProfile)
}