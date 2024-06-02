package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDay

interface SetWeekDayNotificationStateUseCase {
    suspend operator fun invoke(weekDay: WeekDay, isEnabled: Boolean)
}