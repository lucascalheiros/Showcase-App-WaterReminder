package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDayNotificationState
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories.WeekDayNotificationStateRepository
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.SetWeekDayNotificationStateUseCase

internal class SetWeekDayNotificationStateUseCaseImpl(
    private val weekDayNotificationStateRepository: WeekDayNotificationStateRepository
): SetWeekDayNotificationStateUseCase {
    override suspend fun invoke(
        dayTime: DayTime,
        weekDayNotificationState: List<WeekDayNotificationState>
    ) {
        weekDayNotificationStateRepository.updateWeekDayState(dayTime, weekDayNotificationState)
    }
}