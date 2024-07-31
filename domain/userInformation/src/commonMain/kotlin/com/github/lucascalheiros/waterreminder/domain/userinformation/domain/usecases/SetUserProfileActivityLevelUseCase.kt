package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.userinformation.domain.models.ActivityLevel

interface SetUserProfileActivityLevelUseCase {
    suspend operator fun invoke(activityLevel: ActivityLevel)
}