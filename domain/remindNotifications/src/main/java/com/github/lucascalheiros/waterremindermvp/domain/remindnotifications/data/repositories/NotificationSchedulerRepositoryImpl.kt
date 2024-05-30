package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.data.repositories

import com.github.lucascalheiros.waterremindermvp.data.notificationprovider.framework.NotificationProviderWrapper
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.repositories.NotificationSchedulerRepository
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.DayTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class NotificationSchedulerRepositoryImpl(
    private val notificationProviderWrapper: NotificationProviderWrapper
) : NotificationSchedulerRepository {
    override suspend fun setup() {
        notificationProviderWrapper.setup()
    }

    override suspend fun scheduleRemindNotification(dayTime: DayTime) {
        return notificationProviderWrapper.scheduleRemindNotification(dayTime.dayMinutes)
    }

    override suspend fun cancelRemindNotification(dayTime: DayTime) {
        return notificationProviderWrapper.cancelRemindNotification(dayTime.dayMinutes)
    }

    override suspend fun allRemindNotifications(): List<DayTime> {
        return notificationProviderWrapper.allRemindNotifications()
            .map { DayTime.fromDayMinutes(it) }
    }

    override fun allRemindNotificationsFlow(): Flow<List<DayTime>> {
        return notificationProviderWrapper.allRemindNotificationsFlow()
            .map { list -> list.map { DayTime.fromDayMinutes(it) } }
    }
}