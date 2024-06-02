package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDayNotificationState
import kotlinx.coroutines.flow.Flow

interface GetWeekDayNotificationStateUseCase {
    operator fun invoke(): Flow<List<WeekDayNotificationState>>
    suspend fun single(): List<WeekDayNotificationState>
}