package com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.model.FirstAccessNotificationData
import kotlinx.coroutines.flow.Flow

interface GetFirstAccessNotificationDataUseCase {
    operator fun invoke(): Flow<FirstAccessNotificationData>
}