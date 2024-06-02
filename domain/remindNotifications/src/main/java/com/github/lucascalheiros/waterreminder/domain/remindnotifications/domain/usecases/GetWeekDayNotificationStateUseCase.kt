package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDayNotificationState
import com.github.lucascalheiros.waterreminder.common.util.requests.AsyncRequest
import kotlinx.coroutines.flow.Flow

interface GetWeekDayNotificationStateUseCase {
    operator fun invoke(request: AsyncRequest.Continuous): Flow<List<WeekDayNotificationState>>
    suspend operator fun invoke(request: AsyncRequest.Single): List<WeekDayNotificationState>
}