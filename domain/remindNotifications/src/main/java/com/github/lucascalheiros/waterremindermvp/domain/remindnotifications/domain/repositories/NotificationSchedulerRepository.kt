package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.repositories

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.DayTime

interface NotificationSchedulerRepository {
    suspend fun setup()
    suspend fun scheduleRemindNotification(dayTime: DayTime)
    suspend fun cancelRemindNotification(dayTime: DayTime)
    suspend fun allRemindNotifications(): List<DayTime>
}