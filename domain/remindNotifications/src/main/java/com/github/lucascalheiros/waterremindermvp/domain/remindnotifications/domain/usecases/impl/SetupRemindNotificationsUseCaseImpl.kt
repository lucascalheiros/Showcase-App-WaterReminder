package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.impl

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.repositories.NotificationSchedulerRepository
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.SetupRemindNotificationsUseCase

internal class SetupRemindNotificationsUseCaseImpl(
    private val notificationSchedulerRepository: NotificationSchedulerRepository
) : SetupRemindNotificationsUseCase {
    override suspend fun invoke() {
        notificationSchedulerRepository.setup()
    }
}