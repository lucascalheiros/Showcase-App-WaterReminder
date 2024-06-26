package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories.NotificationSchedulerRepository
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.DeleteScheduledNotificationUseCase

internal class DeleteScheduledNotificationUseCaseImpl(
    private val notificationSchedulerRepository: NotificationSchedulerRepository
)  : DeleteScheduledNotificationUseCase {
    override suspend fun invoke(dayTime: DayTime) {
        notificationSchedulerRepository.cancelRemindNotification(dayTime)
    }

}