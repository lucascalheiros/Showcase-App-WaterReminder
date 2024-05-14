package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.impl

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.frameworks.NotificationProvider
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.DeleteScheduledNotificationUseCase

internal class DeleteScheduledNotificationUseCaseImpl(
    private val notificationProvider: NotificationProvider
)  : DeleteScheduledNotificationUseCase {
    override suspend fun invoke(dayTime: DayTime) {
        notificationProvider.cancelRemindNotification(dayTime)
    }

}