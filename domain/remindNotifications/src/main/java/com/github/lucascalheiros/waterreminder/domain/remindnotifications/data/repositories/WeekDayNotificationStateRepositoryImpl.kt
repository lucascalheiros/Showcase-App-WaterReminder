package com.github.lucascalheiros.waterreminder.domain.remindnotifications.data.repositories

import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.NotificationWeekDaysDataSource
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDay
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDayNotificationState
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories.WeekDayNotificationStateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class WeekDayNotificationStateRepositoryImpl(
    private val notificationWeekDaysDataSource: NotificationWeekDaysDataSource
) : WeekDayNotificationStateRepository {

    override suspend fun updateWeekDayState(weekDay: WeekDay, isEnabled: Boolean) {
        if (isEnabled) {
            notificationWeekDaysDataSource.addWeekDay(weekDay.dayNumber)
        } else {
            notificationWeekDaysDataSource.removeWeekDay(weekDay.dayNumber)
        }
    }

    override fun weekDayNotificationStateFlow(): Flow<List<WeekDayNotificationState>> {
        return notificationWeekDaysDataSource.weekDaysEnabledFlow().map { list ->
            WeekDay.entries.map { WeekDayNotificationState(it, list.contains(it.dayNumber)) }
        }
    }

    override suspend fun weekDayNotificationState(): List<WeekDayNotificationState> {
        val list = notificationWeekDaysDataSource.weekDaysEnabled()
        return WeekDay.entries.map { WeekDayNotificationState(it, list.contains(it.dayNumber)) }
    }

}