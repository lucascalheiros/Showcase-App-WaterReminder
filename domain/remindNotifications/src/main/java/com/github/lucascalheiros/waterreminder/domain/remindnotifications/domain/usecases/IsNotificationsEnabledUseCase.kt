package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases

import com.github.lucascalheiros.waterreminder.common.util.requests.AsyncRequest
import kotlinx.coroutines.flow.Flow

interface IsNotificationsEnabledUseCase {
    suspend operator fun invoke(request: AsyncRequest.Single): Boolean
    operator fun invoke(request: AsyncRequest.Continuous): Flow<Boolean>
}