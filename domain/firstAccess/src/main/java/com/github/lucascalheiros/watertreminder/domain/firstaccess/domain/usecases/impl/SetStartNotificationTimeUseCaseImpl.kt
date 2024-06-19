package com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.usecases.impl

import com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.repositories.FirstAccessNotificationDataRepository
import com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.usecases.SetStartNotificationTimeUseCase
import kotlinx.datetime.LocalTime

class SetStartNotificationTimeUseCaseImpl(
    private val repository: FirstAccessNotificationDataRepository
): SetStartNotificationTimeUseCase {
    override suspend operator fun invoke(time: LocalTime) {
        repository.setNotificationStartTime(time)
    }
}