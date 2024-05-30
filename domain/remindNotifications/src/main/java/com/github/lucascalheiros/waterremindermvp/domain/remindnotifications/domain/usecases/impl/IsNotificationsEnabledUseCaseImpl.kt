package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.impl

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.IsNotificationsEnabledUseCase
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.requests.AsyncRequest
import kotlinx.coroutines.flow.Flow

class IsNotificationsEnabledUseCaseImpl: IsNotificationsEnabledUseCase {
    override suspend fun invoke(request: AsyncRequest.Single): Boolean {
        TODO("Not yet implemented")
    }

    override fun invoke(request: AsyncRequest.Continuous): Flow<Boolean> {
        TODO("Not yet implemented")
    }
}