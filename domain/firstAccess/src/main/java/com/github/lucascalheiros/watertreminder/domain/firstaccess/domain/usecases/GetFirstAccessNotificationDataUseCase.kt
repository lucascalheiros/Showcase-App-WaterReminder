package com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.usecases

import com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.model.FirstAccessNotificationData
import kotlinx.coroutines.flow.Flow

interface GetFirstAccessNotificationDataUseCase {
    operator fun invoke(): Flow<FirstAccessNotificationData>
}