package com.github.lucascalheiros.waterremindermvp.data.notificationprovider.data

import kotlinx.coroutines.flow.Flow

interface NotificationWeekDaysDataSource {
    suspend fun weekDaysEnabled(): List<Int>
    fun weekDaysEnabledFlow(): Flow<List<Int>>
    suspend fun removeWeekDay(weekDayValue: Int)
    suspend fun addWeekDay(weekDayValue: Int)
}