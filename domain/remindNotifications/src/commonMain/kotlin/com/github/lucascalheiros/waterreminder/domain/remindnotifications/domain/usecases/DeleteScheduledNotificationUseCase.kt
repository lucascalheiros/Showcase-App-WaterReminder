package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface DeleteScheduledNotificationUseCase {
    @NativeCoroutines
    suspend operator fun invoke(request: DeleteScheduledNotificationRequest)
}

sealed interface DeleteScheduledNotificationRequest {
    data class Single(val dayTime: DayTime): DeleteScheduledNotificationRequest
    data object All: DeleteScheduledNotificationRequest
}
