package com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases

import kotlinx.datetime.LocalTime

interface SetNotificationFrequencyTimeUseCase {
    suspend operator fun invoke(time: LocalTime)
}