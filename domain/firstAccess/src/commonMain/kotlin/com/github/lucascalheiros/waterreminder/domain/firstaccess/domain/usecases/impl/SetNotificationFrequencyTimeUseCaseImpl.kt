package com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.repositories.FirstAccessNotificationDataRepository
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.SetNotificationFrequencyTimeUseCase
import kotlinx.datetime.LocalTime

class SetNotificationFrequencyTimeUseCaseImpl(
    private val repository: FirstAccessNotificationDataRepository
): SetNotificationFrequencyTimeUseCase {
    override suspend fun invoke(time: LocalTime) {
        repository.setNotificationPeriod(time)
    }
}