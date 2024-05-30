package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.WeekDay

interface SetWeekDayNotificationStateUseCase {
    suspend operator fun invoke(weekDay: WeekDay, isEnabled: Boolean)
}