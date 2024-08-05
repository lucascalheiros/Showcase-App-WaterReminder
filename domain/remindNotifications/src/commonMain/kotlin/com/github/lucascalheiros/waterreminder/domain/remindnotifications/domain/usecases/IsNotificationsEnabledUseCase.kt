package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface IsNotificationsEnabledUseCase {
    @NativeCoroutines
    suspend fun single(): Boolean
    @NativeCoroutines
    operator fun invoke(): Flow<Boolean>
}