package com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases

import kotlinx.datetime.LocalTime

interface SetStopNotificationTimeUseCase {
    suspend operator fun invoke(time: LocalTime)
}