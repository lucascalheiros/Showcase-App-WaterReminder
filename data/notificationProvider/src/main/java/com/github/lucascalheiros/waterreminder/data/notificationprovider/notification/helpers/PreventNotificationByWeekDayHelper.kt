package com.github.lucascalheiros.waterreminder.data.notificationprovider.notification.helpers

import android.content.Intent
import com.github.lucascalheiros.waterreminder.common.util.logDebug
import com.github.lucascalheiros.waterreminder.data.notificationprovider.framework.AlarmManagerWrapperImpl
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDay
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.GetScheduledNotificationsUseCase
import java.util.Calendar

internal class PreventNotificationByWeekDayHelper(
    private val getScheduledNotificationsUseCase: GetScheduledNotificationsUseCase,
) {
    suspend fun shouldPreventNotificationFromIntent(intent: Intent): Boolean {
        val dayTimeInMinutes = intent.getIntExtra(AlarmManagerWrapperImpl.MINUTES_OF_DAY_DATA_EXTRA, -1)
        logDebug("::shouldPreventNotificationFromIntent dayTimeInMinutes $dayTimeInMinutes")
        if (dayTimeInMinutes == -1) {
            return true
        }
        val dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1
        logDebug("::shouldPreventNotificationFromIntent dayOfWeek $dayOfWeek")
        val weekDay = WeekDay.fromDayNumber(dayOfWeek) ?: return true
        return getScheduledNotificationsUseCase.single().find { it.dayTime.dayMinutes == dayTimeInMinutes }.let {
            val weekState = it?.weekState ?: return true
            when (weekDay) {
                WeekDay.Sunday -> weekState.sundayEnabled
                WeekDay.Monday -> weekState.mondayEnabled
                WeekDay.Tuesday -> weekState.tuesdayEnabled
                WeekDay.Wednesday -> weekState.wednesdayEnabled
                WeekDay.Thursday -> weekState.thursdayEnabled
                WeekDay.Friday -> weekState.fridayEnabled
                WeekDay.Saturday -> weekState.saturdayEnabled
            }.not()
        }
    }
}