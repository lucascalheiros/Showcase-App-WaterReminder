package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDayNotificationState
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories.WeekDayNotificationStateRepository
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.GetWeekDayNotificationStateUseCase
import kotlinx.coroutines.flow.Flow

internal class GetWeekDayNotificationStateUseCaseImpl(
    private val weekDayNotificationStateRepository: WeekDayNotificationStateRepository
) : GetWeekDayNotificationStateUseCase {
    override fun invoke(): Flow<List<WeekDayNotificationState>> {
        return weekDayNotificationStateRepository.weekDayNotificationStateFlow()
    }

    override suspend fun single(): List<WeekDayNotificationState> {
        return weekDayNotificationStateRepository.weekDayNotificationState()
    }
}