package com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.model.FirstAccessNotificationData
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.repositories.FirstAccessNotificationDataRepository
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.GetFirstAccessNotificationDataUseCase
import kotlinx.coroutines.flow.Flow

class GetFirstAccessNotificationDataUseCaseImpl(
    private val repository: FirstAccessNotificationDataRepository
): GetFirstAccessNotificationDataUseCase {
    override fun invoke(): Flow<FirstAccessNotificationData> {
        return repository.firstAccessNotificationFlow()
    }
}