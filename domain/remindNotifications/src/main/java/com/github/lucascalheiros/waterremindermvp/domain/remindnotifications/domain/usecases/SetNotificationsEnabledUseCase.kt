package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases

interface SetNotificationsEnabledUseCase {
    suspend operator fun invoke(isEnabled: Boolean)
}