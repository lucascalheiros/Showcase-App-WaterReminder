package com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.impl

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.CreateScheduleNotificationUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.SetNotificationsEnabledUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.model.FirstAccessNotificationData
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.repositories.FirstUseFlagsRepository
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.CompleteFirstAccessFlowUseCase
import com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.usecases.GetFirstAccessNotificationDataUseCase
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekState
import kotlinx.coroutines.flow.first
import kotlinx.datetime.LocalTime

internal class CompleteFirstAccessFlowUseCaseImpl(
    private val firstUseFlagsRepository: FirstUseFlagsRepository,
    private val getFirstAccessNotificationDataUseCase: GetFirstAccessNotificationDataUseCase,
    private val createScheduleNotificationUseCase: CreateScheduleNotificationUseCase,
    private val setNotificationsEnabledUseCase: SetNotificationsEnabledUseCase
) : CompleteFirstAccessFlowUseCase {

    override suspend fun invoke() {
        val notificationData = getFirstAccessNotificationDataUseCase().first()
        setNotificationsEnabledUseCase(notificationData.shouldEnable)
        notificationData.notificationListFromRange().forEach {
            createScheduleNotificationUseCase(it.toDayTime(), WeekState(
                sundayEnabled = true,
                mondayEnabled = true,
                tuesdayEnabled = true,
                wednesdayEnabled = true,
                thursdayEnabled = true,
                fridayEnabled = true,
                saturdayEnabled = true
            ))
        }
        firstUseFlagsRepository.setFirstAccessCompletedFlag(true)
    }

    private fun LocalTime.toDayTime(): DayTime {
        return DayTime(hour, minute)
    }

    private fun FirstAccessNotificationData.notificationListFromRange(): List<LocalTime> {
        val startTimeInSeconds = startTime.toSecondOfDay()
        val stopTimeInSeconds = stopTime.toSecondOfDay()
        val frequencyInSeconds = frequencyTime.toSecondOfDay()
        val secondsInInterval = stopTimeInSeconds - startTimeInSeconds
        val numberOfNotifications = secondsInInterval / frequencyInSeconds
        return (0 .. numberOfNotifications).map {
            LocalTime.fromSecondOfDay(startTimeInSeconds + frequencyInSeconds * it)
        }
    }
}