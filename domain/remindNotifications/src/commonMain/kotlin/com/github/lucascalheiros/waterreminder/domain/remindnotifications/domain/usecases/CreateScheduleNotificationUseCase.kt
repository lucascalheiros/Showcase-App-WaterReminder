package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekState
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.NotificationsPeriod
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface CreateScheduleNotificationUseCase {
    @NativeCoroutines
    suspend operator fun invoke(dayTime: DayTime,  weekState: WeekState)
    @NativeCoroutines
    suspend operator fun invoke(period: NotificationsPeriod, weekState: WeekState)
}