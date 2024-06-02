package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories.NotificationSchedulerRepository
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.GetScheduledNotificationsUseCase
import kotlinx.coroutines.flow.Flow

internal class GetScheduledNotificationsUseCaseImpl(
    private val notificationSchedulerRepository: NotificationSchedulerRepository
)  : GetScheduledNotificationsUseCase {
    override suspend fun single(): List<DayTime> {
        return notificationSchedulerRepository.allRemindNotifications()
    }

    override fun invoke(): Flow<List<DayTime>> {
        return notificationSchedulerRepository.allRemindNotificationsFlow()
    }
}