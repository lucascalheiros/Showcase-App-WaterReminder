package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.impl

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.frameworks.NotificationProvider
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.ScheduleNotificationUseCase

internal class ScheduleNotificationUseCaseImpl(
    private val notificationProvider: NotificationProvider
)  : ScheduleNotificationUseCase {
    override suspend fun invoke(dayTime: DayTime) {
        return notificationProvider.scheduleRemindNotification(dayTime)
    }

}