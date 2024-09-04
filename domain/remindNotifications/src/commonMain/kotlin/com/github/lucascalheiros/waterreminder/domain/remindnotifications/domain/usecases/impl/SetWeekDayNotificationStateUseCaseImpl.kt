package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekState
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories.NotificationSchedulerRepository
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.SetWeekDayNotificationStateUseCase

internal class SetWeekDayNotificationStateUseCaseImpl(
    private val weekDayNotificationStateRepository: NotificationSchedulerRepository
): SetWeekDayNotificationStateUseCase {
    override suspend fun invoke(
        dayTime: DayTime,
        weekState: WeekState
    ) {
        weekDayNotificationStateRepository.updateWeekDayState(dayTime, weekState)
    }
}