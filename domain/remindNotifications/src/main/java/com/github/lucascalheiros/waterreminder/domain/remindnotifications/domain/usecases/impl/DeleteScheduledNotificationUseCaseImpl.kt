package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories.NotificationSchedulerRepository
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.DeleteScheduledNotificationRequest
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.DeleteScheduledNotificationUseCase

internal class DeleteScheduledNotificationUseCaseImpl(
    private val notificationSchedulerRepository: NotificationSchedulerRepository
) : DeleteScheduledNotificationUseCase {
    override suspend fun invoke(request: DeleteScheduledNotificationRequest) {
        when (request) {
            DeleteScheduledNotificationRequest.All -> {
                notificationSchedulerRepository.allRemindNotifications().forEach {
                    notificationSchedulerRepository.cancelRemindNotification(it)
                }
            }
            is DeleteScheduledNotificationRequest.Single -> notificationSchedulerRepository.cancelRemindNotification(
                request.dayTime
            )
        }
    }

}