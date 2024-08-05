package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDayNotificationState
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

interface GetWeekDayNotificationStateUseCase {
    @NativeCoroutines
    operator fun invoke(dayTime: DayTime): Flow<List<WeekDayNotificationState>>
}