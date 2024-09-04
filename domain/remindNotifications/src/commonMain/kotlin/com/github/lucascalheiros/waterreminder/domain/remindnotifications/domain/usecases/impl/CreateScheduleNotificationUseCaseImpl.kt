package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories.NotificationSchedulerRepository
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.NotificationsPeriod
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekState
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.CreateScheduleNotificationUseCase

internal class CreateScheduleNotificationUseCaseImpl(
    private val notificationSchedulerRepository: NotificationSchedulerRepository
)  : CreateScheduleNotificationUseCase {
    override suspend fun invoke(dayTime: DayTime, weekState: WeekState) {
        return notificationSchedulerRepository.scheduleRemindNotification(dayTime, weekState)
    }

    override suspend fun invoke(period: NotificationsPeriod, weekState: WeekState) {
        val dayInMinutes = 24 * 60
        val startMinute = period.start.dayMinutes
        val stopMinute = period.stop.dayMinutes.let {
            if (startMinute > it) {
                it + dayInMinutes
            } else {
                it
            }
        }
        val periodMinutes = period.period.dayMinutes
        val numberOfNotifications = (stopMinute - startMinute) / periodMinutes
        (0..numberOfNotifications).map {
            (startMinute + it * periodMinutes) % dayInMinutes
        }.forEach {
            notificationSchedulerRepository.scheduleRemindNotification(
                DayTime.fromDayMinutes(it),
                weekState
            )
        }
    }
}
