package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.UserProfile
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface SetUserProfileUseCase {
    @NativeCoroutines
    suspend operator fun invoke(userProfile: UserProfile)
}