package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.impl

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.frameworks.NotificationProvider
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.GetScheduledNotificationsUseCase

internal class GetScheduledNotificationsUseCaseImpl(
    private val notificationProvider: NotificationProvider
)  : GetScheduledNotificationsUseCase {
    override suspend fun invoke(): List<DayTime> {
        return notificationProvider.allRemindNotifications()
    }
}