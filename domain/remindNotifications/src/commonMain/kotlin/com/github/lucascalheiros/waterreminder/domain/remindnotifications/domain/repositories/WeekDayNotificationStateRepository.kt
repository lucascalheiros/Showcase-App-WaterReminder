package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDayNotificationState
import kotlinx.coroutines.flow.Flow

interface WeekDayNotificationStateRepository {
    suspend fun updateWeekDayState(dayTime: DayTime, weekDayStates: List<WeekDayNotificationState>)
    fun weekDayNotificationStateFlow(dayTime: DayTime): Flow<List<WeekDayNotificationState>>
    suspend fun weekDayNotificationState(dayTime: DayTime): List<WeekDayNotificationState>
}