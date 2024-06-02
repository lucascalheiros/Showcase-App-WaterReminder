package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import kotlinx.coroutines.flow.Flow

interface NotificationSchedulerRepository {
    suspend fun setup()
    suspend fun scheduleRemindNotification(dayTime: DayTime)
    suspend fun cancelRemindNotification(dayTime: DayTime)
    suspend fun allRemindNotifications(): List<DayTime>
    fun allRemindNotificationsFlow(): Flow<List<DayTime>>
    suspend fun isNotificationEnabled(): Boolean
    fun isNotificationEnabledFlow(): Flow<Boolean>
    suspend fun setNotificationEnabled(isEnabled: Boolean)

}