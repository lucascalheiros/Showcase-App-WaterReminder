package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.data.repositories

import com.github.lucascalheiros.waterremindermvp.data.notificationprovider.framework.NotificationProviderWrapper
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.WeekDay
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.WeekDayNotificationState
import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.repositories.WeekDayNotificationStateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class WeekDayNotificationStateRepositoryImpl(
    private val notificationProviderWrapper: NotificationProviderWrapper
) : WeekDayNotificationStateRepository {
    override suspend fun updateWeekDayState(weekDay: WeekDay, isEnabled: Boolean) {
        if (isEnabled) {
            notificationProviderWrapper.addWeekDay(weekDay.dayNumber)
        } else {
            notificationProviderWrapper.removeWeekDay(weekDay.dayNumber)
        }
    }

    override fun weekDayNotificationStateFlow(): Flow<List<WeekDayNotificationState>> {
        return notificationProviderWrapper.allRemindNotificationsFlow().map { list ->
            WeekDay.entries.map { WeekDayNotificationState(it, list.contains(it.dayNumber)) }
        }
    }

    override suspend fun weekDayNotificationState(): List<WeekDayNotificationState> {
        val list = notificationProviderWrapper.weekDaysEnabled()
        return WeekDay.entries.map { WeekDayNotificationState(it, list.contains(it.dayNumber)) }
    }

}