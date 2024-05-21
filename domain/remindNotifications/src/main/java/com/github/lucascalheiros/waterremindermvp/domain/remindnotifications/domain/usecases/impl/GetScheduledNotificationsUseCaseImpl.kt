package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.impl

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.repositories.NotificationSchedulerRepository
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.GetScheduledNotificationsUseCase

internal class GetScheduledNotificationsUseCaseImpl(
    private val notificationSchedulerRepository: NotificationSchedulerRepository
)  : GetScheduledNotificationsUseCase {
    override suspend fun invoke(): List<DayTime> {
        return notificationSchedulerRepository.allRemindNotifications()
    }
}