package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.impl

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.WeekDayNotificationState
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.repositories.WeekDayNotificationStateRepository
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.GetWeekDayNotificationStateUseCase
import com.github.lucascalheiros.waterremindermvp.common.util.requests.AsyncRequest
import kotlinx.coroutines.flow.Flow

internal class GetWeekDayNotificationStateUseCaseImpl(
    private val weekDayNotificationStateRepository: WeekDayNotificationStateRepository
) : GetWeekDayNotificationStateUseCase {
    override fun invoke(request: AsyncRequest.Continuous): Flow<List<WeekDayNotificationState>> {
        return weekDayNotificationStateRepository.weekDayNotificationStateFlow()
    }

    override suspend fun invoke(request: AsyncRequest.Single): List<WeekDayNotificationState> {
        return weekDayNotificationStateRepository.weekDayNotificationState()
    }
}