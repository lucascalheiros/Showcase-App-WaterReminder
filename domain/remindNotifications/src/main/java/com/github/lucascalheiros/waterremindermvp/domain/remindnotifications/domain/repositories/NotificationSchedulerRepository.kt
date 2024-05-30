package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.repositories

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.DayTime
import kotlinx.coroutines.flow.Flow

interface NotificationSchedulerRepository {
    suspend fun setup()
    suspend fun scheduleRemindNotification(dayTime: DayTime)
    suspend fun cancelRemindNotification(dayTime: DayTime)
    suspend fun allRemindNotifications(): List<DayTime>
    fun allRemindNotificationsFlow(): Flow<List<DayTime>>
}