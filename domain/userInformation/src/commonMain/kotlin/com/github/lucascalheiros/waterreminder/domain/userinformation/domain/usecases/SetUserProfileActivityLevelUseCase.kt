package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.ActivityLevel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface SetUserProfileActivityLevelUseCase {
    @NativeCoroutines
    suspend operator fun invoke(activityLevel: ActivityLevel)
}