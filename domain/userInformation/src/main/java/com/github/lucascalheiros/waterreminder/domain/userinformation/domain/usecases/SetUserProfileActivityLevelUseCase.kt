package com.github.lucascalheiros.waterreminder.domain.userinformation.domain.usecases

interface SetUserProfileActivityLevelUseCase {
    suspend operator fun invoke(activityWeekDays: Int)
}