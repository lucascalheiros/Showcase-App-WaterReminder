package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.common.util.requests.AsyncRequest
import kotlinx.coroutines.flow.Flow

interface GetScheduledNotificationsUseCase {
    suspend operator fun invoke(request: AsyncRequest.Single): List<DayTime>
    operator fun invoke(request: AsyncRequest.Continuous): Flow<List<DayTime>>
}