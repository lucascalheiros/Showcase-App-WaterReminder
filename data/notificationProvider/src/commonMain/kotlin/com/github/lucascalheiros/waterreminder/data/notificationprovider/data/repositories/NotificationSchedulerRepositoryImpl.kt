package com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories

import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources.NotificationEnabledDataSource
import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources.NotificationSchedulerDataSource
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.NotificationInfo
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekState
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories.NotificationSchedulerRepository
import kotlinx.coroutines.flow.Flow

internal class NotificationSchedulerRepositoryImpl(
    private val notificationEnabledDataSource: NotificationEnabledDataSource,
    private val notificationSchedulerDataSource: NotificationSchedulerDataSource
) : NotificationSchedulerRepository {

    override suspend fun scheduleRemindNotification(
        dayTime: DayTime,
        weekState: WeekState
    ) {
        return notificationSchedulerDataSource.scheduleRemindNotification(dayTime, weekState)
    }

    override suspend fun cancelRemindNotification(dayTime: DayTime) {
        return notificationSchedulerDataSource.cancelRemindNotification(dayTime.dayMinutes)
    }

    override suspend fun allRemindNotifications(): List<NotificationInfo> {
        return notificationSchedulerDataSource.allRemindNotifications()
    }

    override fun allRemindNotificationsFlow(): Flow<List<NotificationInfo>> {
        return notificationSchedulerDataSource.allRemindNotificationsFlow()
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

    override suspend fun updateWeekDayState(
        dayTime: DayTime,
        weekState: WeekState
    ) {
        notificationSchedulerDataSource.updateWeekDays(dayTime, weekState)
    }
}