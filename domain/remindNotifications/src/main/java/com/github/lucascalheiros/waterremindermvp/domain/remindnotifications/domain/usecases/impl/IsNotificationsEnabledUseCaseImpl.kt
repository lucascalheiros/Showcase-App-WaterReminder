package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.impl

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.repositories.NotificationSchedulerRepository
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.IsNotificationsEnabledUseCase
import com.github.lucascalheiros.waterremindermvp.common.util.requests.AsyncRequest
import kotlinx.coroutines.flow.Flow

class IsNotificationsEnabledUseCaseImpl(
    private val notificationSchedulerRepository: NotificationSchedulerRepository
): IsNotificationsEnabledUseCase {
    override suspend fun invoke(request: AsyncRequest.Single): Boolean {
        return notificationSchedulerRepository.isNotificationEnabled()
    }

    override fun invoke(request: AsyncRequest.Continuous): Flow<Boolean> {
        return notificationSchedulerRepository.isNotificationEnabledFlow()
    }
}