package com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.usecases.impl

import com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.repositories.FirstAccessNotificationDataRepository
import com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.usecases.SetFirstAccessNotificationStateUseCase

class SetFirstAccessNotificationStateUseCaseImpl(
    private val repository: FirstAccessNotificationDataRepository
): SetFirstAccessNotificationStateUseCase {
    override suspend fun invoke(value: Boolean) {
        repository.setNotificationState(value)
    }
}