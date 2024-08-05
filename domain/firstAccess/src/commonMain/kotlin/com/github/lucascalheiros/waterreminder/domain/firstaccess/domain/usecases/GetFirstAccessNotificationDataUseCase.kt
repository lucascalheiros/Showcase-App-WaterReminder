package com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.model.FirstAccessNotificationData
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface GetFirstAccessNotificationDataUseCase {
    @NativeCoroutines
    operator fun invoke(): Flow<FirstAccessNotificationData>
}