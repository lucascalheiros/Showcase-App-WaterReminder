package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories.NotificationSchedulerRepository
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.SetNotificationsEnabledUseCase

class SetNotificationsEnabledUseCaseImpl(
    private val notificationSchedulerRepository: NotificationSchedulerRepository
) : SetNotificationsEnabledUseCase {
    override suspend fun invoke(isEnabled: Boolean) {
        notificationSchedulerRepository.setNotificationEnabled(isEnabled)
    }

}