package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDay
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDayNotificationState
import kotlinx.coroutines.flow.Flow

interface WeekDayNotificationStateRepository {
    suspend fun updateWeekDayState(weekDay: WeekDay, isEnabled: Boolean)
    fun weekDayNotificationStateFlow(): Flow<List<WeekDayNotificationState>>
    suspend fun weekDayNotificationState(): List<WeekDayNotificationState>
}