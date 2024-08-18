package com.github.lucascalheiros.waterreminder.domain.remindnotifications

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.CreateScheduleNotificationUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.DeleteScheduledNotificationUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.GetScheduledNotificationsUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.IsNotificationsEnabledUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.SetNotificationsEnabledUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.SetWeekDayNotificationStateUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class RemindNotificationsInjector: KoinComponent {
    fun deleteScheduledNotificationUseCase(): DeleteScheduledNotificationUseCase = get()
    fun getScheduledNotificationsUseCase(): GetScheduledNotificationsUseCase = get()
    fun createScheduleNotificationUseCase(): CreateScheduleNotificationUseCase = get()
    fun setWeekDayNotificationStateUseCase(): SetWeekDayNotificationStateUseCase = get()
    fun isNotificationsEnabledUseCase(): IsNotificationsEnabledUseCase = get()
    fun setNotificationsEnabledUseCase(): SetNotificationsEnabledUseCase = get()
}