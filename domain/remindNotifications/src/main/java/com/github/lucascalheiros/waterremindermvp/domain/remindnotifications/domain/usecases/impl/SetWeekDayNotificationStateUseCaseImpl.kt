package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.impl

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.WeekDay
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.repositories.WeekDayNotificationStateRepository
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.SetWeekDayNotificationStateUseCase

internal class SetWeekDayNotificationStateUseCaseImpl(
    private val weekDayNotificationStateRepository: WeekDayNotificationStateRepository
): SetWeekDayNotificationStateUseCase {
    override suspend fun invoke(weekDay: WeekDay, isEnabled: Boolean) {
        weekDayNotificationStateRepository.updateWeekDayState(weekDay, isEnabled)
    }
}