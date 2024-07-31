package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import kotlinx.coroutines.flow.Flow

interface GetScheduledNotificationsUseCase {
    suspend fun single(): List<DayTime>
    operator fun invoke(): Flow<List<DayTime>>
}