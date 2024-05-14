package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.DayTime

interface GetScheduledNotificationsUseCase {
    suspend operator fun invoke(): List<DayTime>
}