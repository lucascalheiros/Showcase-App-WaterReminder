package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.NotificationInfo
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface GetScheduledNotificationsUseCase {
    @NativeCoroutines
    suspend fun single(): List<NotificationInfo>
    @NativeCoroutines
    operator fun invoke(): Flow<List<NotificationInfo>>
    @NativeCoroutines
    operator fun invoke(dayTime: DayTime): Flow<NotificationInfo>
}