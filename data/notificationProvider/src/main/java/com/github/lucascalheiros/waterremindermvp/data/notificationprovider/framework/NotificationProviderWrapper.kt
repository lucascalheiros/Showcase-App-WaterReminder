package com.github.lucascalheiros.waterremindermvp.data.notificationprovider.framework

interface NotificationProviderWrapper {
    suspend fun setup()
    suspend fun scheduleRemindNotification(dayTimeInMinutes: Int)
    suspend fun cancelRemindNotification(dayTimeInMinutes: Int)
    suspend fun allRemindNotifications(): List<Int>
}