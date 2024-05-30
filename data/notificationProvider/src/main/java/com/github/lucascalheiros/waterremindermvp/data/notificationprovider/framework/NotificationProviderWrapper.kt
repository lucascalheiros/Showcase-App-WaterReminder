package com.github.lucascalheiros.waterremindermvp.data.notificationprovider.framework

import kotlinx.coroutines.flow.Flow

interface NotificationProviderWrapper {
    suspend fun setup()
    suspend fun scheduleRemindNotification(dayTimeInMinutes: Int)
    suspend fun cancelRemindNotification(dayTimeInMinutes: Int)
    suspend fun allRemindNotifications(): List<Int>
    fun allRemindNotificationsFlow(): Flow<List<Int>>
    suspend fun weekDaysEnabled(): List<Int>
    fun weekDaysEnabledFlow(): Flow<List<Int>>
    suspend fun removeWeekDay(weekDayValue: Int)
    suspend fun addWeekDay(weekDayValue: Int)
}