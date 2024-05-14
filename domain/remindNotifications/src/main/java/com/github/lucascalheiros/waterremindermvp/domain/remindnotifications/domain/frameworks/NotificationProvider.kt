package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.frameworks

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.DayTime

interface NotificationProvider {
    suspend fun setup()
    suspend fun scheduleRemindNotification(dayTime: DayTime)
    suspend fun cancelRemindNotification(dayTime: DayTime)
    suspend fun allRemindNotifications(): List<DayTime>
}