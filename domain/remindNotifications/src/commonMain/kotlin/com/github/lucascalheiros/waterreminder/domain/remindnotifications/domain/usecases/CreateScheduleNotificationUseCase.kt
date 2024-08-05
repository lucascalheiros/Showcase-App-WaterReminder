package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface CreateScheduleNotificationUseCase {
    @NativeCoroutines
    suspend operator fun invoke(dayTime: DayTime)
}