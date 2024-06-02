package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories.NotificationSchedulerRepository
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.CreateScheduleNotificationUseCase

internal class CreateScheduleNotificationUseCaseImpl(
    private val notificationSchedulerRepository: NotificationSchedulerRepository
)  : CreateScheduleNotificationUseCase {
    override suspend fun invoke(dayTime: DayTime) {
        return notificationSchedulerRepository.scheduleRemindNotification(dayTime)
    }

}