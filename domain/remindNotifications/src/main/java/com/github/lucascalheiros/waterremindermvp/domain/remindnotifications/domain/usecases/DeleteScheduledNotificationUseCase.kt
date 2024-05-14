package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.DayTime

interface DeleteScheduledNotificationUseCase {
    suspend operator fun invoke(dayTime: DayTime)
}
