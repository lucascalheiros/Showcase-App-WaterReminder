package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDayNotificationState

interface SetWeekDayNotificationStateUseCase {
    suspend operator fun invoke(dayTime: DayTime, weekDayNotificationState: List<WeekDayNotificationState>)
}