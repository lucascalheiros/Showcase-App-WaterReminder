package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases

import kotlinx.coroutines.flow.Flow

interface IsNotificationsEnabledUseCase {
    suspend fun single(): Boolean
    operator fun invoke(): Flow<Boolean>
}