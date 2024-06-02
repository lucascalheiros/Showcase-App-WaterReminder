package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime

interface DeleteScheduledNotificationUseCase {
    suspend operator fun invoke(dayTime: DayTime)
}
