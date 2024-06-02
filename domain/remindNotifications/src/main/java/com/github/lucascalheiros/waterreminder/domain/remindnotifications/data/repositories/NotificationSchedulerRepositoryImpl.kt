package com.github.lucascalheiros.waterreminder.domain.remindnotifications.data.repositories

import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.NotificationEnabledDataSource
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.NotificationSchedulerWrapperDataSource
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories.NotificationSchedulerRepository
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class NotificationSchedulerRepositoryImpl(
    private val notificationEnabledDataSource: NotificationEnabledDataSource,
    private val notificationSchedulerWrapperDataSource: NotificationSchedulerWrapperDataSource
) : NotificationSchedulerRepository {
    override suspend fun setup() {
        notificationSchedulerWrapperDataSource.setup()
    }

    override suspend fun scheduleRemindNotification(dayTime: DayTime) {
        return notificationSchedulerWrapperDataSource.scheduleRemindNotification(dayTime.dayMinutes)
    }

    override suspend fun cancelRemindNotification(dayTime: DayTime) {
        return notificationSchedulerWrapperDataSource.cancelRemindNotification(dayTime.dayMinutes)
    }

    override suspend fun allRemindNotifications(): List<DayTime> {
        return notificationSchedulerWrapperDataSource.allRemindNotifications()
            .map { DayTime.fromDayMinutes(it) }
    }

    override fun allRemindNotificationsFlow(): Flow<List<DayTime>> {
        return notificationSchedulerWrapperDataSource.allRemindNotificationsFlow()
            .map { list -> list.map { DayTime.fromDayMinutes(it) } }
    }

    override suspend fun isNotificationEnabled(): Boolean {
        return notificationEnabledDataSource.isNotificationEnabled()
    }

    override fun isNotificationEnabledFlow(): Flow<Boolean> {
        return notificationEnabledDataSource.isNotificationEnabledFlow()
    }

    override suspend fun setNotificationEnabled(isEnabled: Boolean) {
        notificationEnabledDataSource.setNotificationEnabled(isEnabled)
    }
}