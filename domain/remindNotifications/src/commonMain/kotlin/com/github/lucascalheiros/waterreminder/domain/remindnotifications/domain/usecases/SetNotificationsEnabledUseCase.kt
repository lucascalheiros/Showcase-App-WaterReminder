package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases

interface SetNotificationsEnabledUseCase {
    suspend operator fun invoke(isEnabled: Boolean)
}