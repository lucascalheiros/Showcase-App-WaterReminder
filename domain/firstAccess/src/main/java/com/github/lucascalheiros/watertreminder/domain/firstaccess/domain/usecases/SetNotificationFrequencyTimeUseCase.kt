package com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.usecases

import kotlinx.datetime.LocalTime

interface SetNotificationFrequencyTimeUseCase {
    suspend operator fun invoke(time: LocalTime)
}