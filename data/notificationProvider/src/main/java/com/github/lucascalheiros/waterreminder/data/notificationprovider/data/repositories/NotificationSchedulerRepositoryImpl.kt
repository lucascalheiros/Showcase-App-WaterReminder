package com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories

import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources.NotificationEnabledDataSource
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources.NotificationSchedulerDataSource
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories.NotificationSchedulerRepository
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class NotificationSchedulerRepositoryImpl(
    private val notificationEnabledDataSource: NotificationEnabledDataSource,
    private val notificationSchedulerDataSource: NotificationSchedulerDataSource
) : NotificationSchedulerRepository {

    override suspend fun scheduleRemindNotification(dayTime: DayTime) {
        return notificationSchedulerDataSource.scheduleRemindNotification(dayTime.dayMinutes)
    }

    override suspend fun cancelRemindNotification(dayTime: DayTime) {
        return notificationSchedulerDataSource.cancelRemindNotification(dayTime.dayMinutes)
    }

    override suspend fun allRemindNotifications(): List<DayTime> {
        return notificationSchedulerDataSource.allRemindNotifications()
            .map { DayTime.fromDayMinutes(it) }
    }

    override fun allRemindNotificationsFlow(): Flow<List<DayTime>> {
        return notificationSchedulerDataSource.allRemindNotificationsFlow()
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