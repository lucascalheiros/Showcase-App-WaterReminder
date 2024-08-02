package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories.NotificationSchedulerRepository
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.IsNotificationsEnabledUseCase
import kotlinx.coroutines.flow.Flow

class IsNotificationsEnabledUseCaseImpl(
    private val notificationSchedulerRepository: NotificationSchedulerRepository
): IsNotificationsEnabledUseCase {
    override suspend fun single(): Boolean {
        return notificationSchedulerRepository.isNotificationEnabled()
    }

    override fun invoke(): Flow<Boolean> {
        return notificationSchedulerRepository.isNotificationEnabledFlow()
    }
}