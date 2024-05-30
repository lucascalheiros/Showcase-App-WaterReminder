package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.impl

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.repositories.NotificationSchedulerRepository
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.GetScheduledNotificationsUseCase
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.requests.AsyncRequest
import kotlinx.coroutines.flow.Flow

internal class GetScheduledNotificationsUseCaseImpl(
    private val notificationSchedulerRepository: NotificationSchedulerRepository
)  : GetScheduledNotificationsUseCase {
    override suspend fun invoke(request: AsyncRequest.Single): List<DayTime> {
        return notificationSchedulerRepository.allRemindNotifications()
    }

    override fun invoke(request: AsyncRequest.Continuous): Flow<List<DayTime>> {
        return notificationSchedulerRepository.allRemindNotificationsFlow()
    }
}