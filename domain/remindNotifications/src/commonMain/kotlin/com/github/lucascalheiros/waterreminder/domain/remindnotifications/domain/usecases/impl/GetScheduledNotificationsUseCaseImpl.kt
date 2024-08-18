package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.NotificationInfo
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories.NotificationSchedulerRepository
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.GetScheduledNotificationsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetScheduledNotificationsUseCaseImpl(
    private val notificationSchedulerRepository: NotificationSchedulerRepository
)  : GetScheduledNotificationsUseCase {
    override suspend fun single(): List<NotificationInfo> {
        return notificationSchedulerRepository.allRemindNotifications()
    }

    override fun invoke(): Flow<List<NotificationInfo>> {
        return notificationSchedulerRepository.allRemindNotificationsFlow()
    }

    override fun invoke(dayTime: DayTime): Flow<NotificationInfo> {
        return notificationSchedulerRepository.allRemindNotificationsFlow().map { infoList ->
            infoList.find { it.dayTime == dayTime }!!
        }
    }
}