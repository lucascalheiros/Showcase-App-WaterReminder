package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.impl

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.repositories.NotificationSchedulerRepository
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.ScheduleNotificationUseCase

internal class ScheduleNotificationUseCaseImpl(
    private val notificationSchedulerRepository: NotificationSchedulerRepository
)  : ScheduleNotificationUseCase {
    override suspend fun invoke(dayTime: DayTime) {
        return notificationSchedulerRepository.scheduleRemindNotification(dayTime)
    }

}