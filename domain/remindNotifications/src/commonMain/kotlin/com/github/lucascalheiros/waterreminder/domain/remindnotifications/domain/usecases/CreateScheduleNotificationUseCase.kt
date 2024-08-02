package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime

interface CreateScheduleNotificationUseCase {
    suspend operator fun invoke(dayTime: DayTime)
}