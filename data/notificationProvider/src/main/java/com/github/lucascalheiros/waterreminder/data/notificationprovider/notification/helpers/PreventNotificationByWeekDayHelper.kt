package com.github.lucascalheiros.waterreminder.data.notificationprovider.notification.helpers

import android.content.Intent
import com.github.lucascalheiros.waterreminder.common.util.logDebug
import com.github.lucascalheiros.waterreminder.data.notificationprovider.framework.AlarmManagerWrapperImpl
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDay
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.usecases.GetWeekDayNotificationStateUseCase
import kotlinx.coroutines.flow.first
import java.util.Calendar

internal class PreventNotificationByWeekDayHelper(
    private val getWeekDayNotificationStateUseCase: GetWeekDayNotificationStateUseCase,
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
        return getWeekDayNotificationStateUseCase(DayTime.fromDayMinutes(dayTimeInMinutes)).first().any {
            !it.isEnabled && it.weekDay == weekDay
        }
    }
}