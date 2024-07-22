package com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories

import com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources.NotificationWeekDaysDataSource
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDayNotificationState
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.repositories.WeekDayNotificationStateRepository
import kotlinx.coroutines.flow.Flow

internal class WeekDayNotificationStateRepositoryImpl(
    private val notificationWeekDaysDataSource: NotificationWeekDaysDataSource
) : WeekDayNotificationStateRepository {

    override suspend fun updateWeekDayState(
        dayTime: DayTime,
        weekDayStates: List<WeekDayNotificationState>
    ) {
        notificationWeekDaysDataSource.setState(dayTime, weekDayStates)
    }

    override fun weekDayNotificationStateFlow(dayTime: DayTime): Flow<List<WeekDayNotificationState>> {
        return notificationWeekDaysDataSource.weekDaysEnabledFlow(dayTime)
    }

    override suspend fun weekDayNotificationState(dayTime: DayTime): List<WeekDayNotificationState> {
        return notificationWeekDaysDataSource.weekDaysEnabled(dayTime)
    }

}