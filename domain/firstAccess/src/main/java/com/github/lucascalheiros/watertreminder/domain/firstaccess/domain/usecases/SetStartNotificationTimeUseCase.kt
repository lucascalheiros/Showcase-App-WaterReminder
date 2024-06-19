package com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.usecases

import kotlinx.datetime.LocalTime

interface SetStartNotificationTimeUseCase {
    suspend operator fun invoke(time: LocalTime)
}