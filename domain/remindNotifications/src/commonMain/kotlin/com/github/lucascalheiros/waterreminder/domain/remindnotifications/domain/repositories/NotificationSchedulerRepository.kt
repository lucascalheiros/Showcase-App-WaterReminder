package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.NotificationInfo
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekState
import kotlinx.coroutines.flow.Flow

interface NotificationSchedulerRepository {
    suspend fun scheduleRemindNotification(dayTime: DayTime, weekState: WeekState)
    suspend fun cancelRemindNotification(dayTime: DayTime)
    suspend fun allRemindNotifications(): List<NotificationInfo>
    fun allRemindNotificationsFlow(): Flow<List<NotificationInfo>>
    suspend fun isNotificationEnabled(): Boolean
    fun isNotificationEnabledFlow(): Flow<Boolean>
    suspend fun setNotificationEnabled(isEnabled: Boolean)
    suspend fun updateWeekDayState(dayTime: DayTime, weekState: WeekState)
}