package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface GetScheduledNotificationsUseCase {
    @NativeCoroutines
    suspend fun single(): List<DayTime>
    @NativeCoroutines
    operator fun invoke(): Flow<List<DayTime>>
}