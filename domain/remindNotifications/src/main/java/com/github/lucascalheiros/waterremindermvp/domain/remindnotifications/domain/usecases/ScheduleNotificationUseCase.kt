package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.DayTime

interface ScheduleNotificationUseCase {
    suspend operator fun invoke(dayTime: DayTime)
}