package com.github.lucascalheiros.waterreminder.data.notificationprovider.data

import kotlinx.coroutines.flow.Flow

interface NotificationSchedulerWrapperDataSource {
    suspend fun setup()
    suspend fun scheduleRemindNotification(dayTimeInMinutes: Int)
    suspend fun cancelRemindNotification(dayTimeInMinutes: Int)
    suspend fun allRemindNotifications(): List<Int>
    fun allRemindNotificationsFlow(): Flow<List<Int>>
}