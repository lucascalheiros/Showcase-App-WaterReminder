package com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.repositories.FirstAccessNotificationDataRepository
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.SetStopNotificationTimeUseCase
import kotlinx.datetime.LocalTime

class SetStopNotificationTimeUseCaseImpl(
    private val repository: FirstAccessNotificationDataRepository
): SetStopNotificationTimeUseCase {
    override suspend fun invoke(time: LocalTime) {
        repository.setNotificationStopTime(time)
    }
}