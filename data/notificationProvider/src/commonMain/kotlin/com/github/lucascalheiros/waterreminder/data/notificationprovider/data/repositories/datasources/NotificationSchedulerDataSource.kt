package com.github.lucascalheiros.waterreminder.data.notificationprovider.data.repositories.datasources

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.github.lucascalheiros.waterreminder.data.notificationprovider.NotificationInfoDb
import com.github.lucascalheiros.waterreminder.data.notificationprovider.ReminderNotificationDatabase
import com.github.lucascalheiros.waterreminder.data.notificationprovider.framework.AlarmManagerWrapper
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.NotificationInfo
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class NotificationSchedulerDataSource(
    private val database: ReminderNotificationDatabase,
    private val alarmManagerWrapper: AlarmManagerWrapper,
    private val ioScope: CoroutineDispatcher
) {

    suspend fun scheduleRemindNotification(
        dayTime: DayTime,
        weekState: WeekState
    ) = withContext(ioScope) {
        database.notificationInfoDbQueries.insert(
            dayTime.dayMinutes.toLong(),
            weekState.sundayEnabled.longValue(),
            weekState.mondayEnabled.longValue(),
            weekState.tuesdayEnabled.longValue(),
            weekState.wednesdayEnabled.longValue(),
            weekState.thursdayEnabled.longValue(),
            weekState.fridayEnabled.longValue(),
            weekState.saturdayEnabled.longValue(),
        )
        alarmManagerWrapper.createAlarmSchedule(dayTime.dayMinutes)
    }

    suspend fun cancelRemindNotification(dayTimeInMinutes: Int) = withContext(ioScope) {
        alarmManagerWrapper.cancelAlarmSchedule(dayTimeInMinutes)
        database.notificationInfoDbQueries.deleteById(dayTimeInMinutes.toLong())
    }

    suspend fun allRemindNotifications(): List<NotificationInfo> = withContext(ioScope) {
        database.notificationInfoDbQueries.selectAll().executeAsList().map {
            NotificationInfo(
                DayTime.fromDayMinutes(it.dayTimeInMinutes.toInt()),
                WeekState(
                    it.sundayEnabled.booleanValue(),
                    it.mondayEnabled.booleanValue(),
                    it.tuesdayEnabled.booleanValue(),
                    it.wednesdayEnabled.booleanValue(),
                    it.thursdayEnabled.booleanValue(),
                    it.fridayEnabled.booleanValue(),
                    it.saturdayEnabled.booleanValue(),
                )
            )
        }
    }

    fun allRemindNotificationsFlow(): Flow<List<NotificationInfo>> {
        return database.notificationInfoDbQueries.selectAll().asFlow().mapToList(ioScope).map { list ->
            list.map {
                NotificationInfo(
                    DayTime.fromDayMinutes(it.dayTimeInMinutes.toInt()),
                    WeekState(
                        it.sundayEnabled.booleanValue(),
                        it.mondayEnabled.booleanValue(),
                        it.tuesdayEnabled.booleanValue(),
                        it.wednesdayEnabled.booleanValue(),
                        it.thursdayEnabled.booleanValue(),
                        it.fridayEnabled.booleanValue(),
                        it.saturdayEnabled.booleanValue(),
                    )
                )
            }
        }
    }

    suspend fun updateWeekDays(
        dayTime: DayTime,
        weekState: WeekState
    ) = withContext(ioScope) {
        database.notificationInfoDbQueries.update(
            NotificationInfoDb(
                dayTime.dayMinutes.toLong(),
                weekState.sundayEnabled.longValue(),
                weekState.mondayEnabled.longValue(),
                weekState.tuesdayEnabled.longValue(),
                weekState.wednesdayEnabled.longValue(),
                weekState.thursdayEnabled.longValue(),
                weekState.fridayEnabled.longValue(),
                weekState.saturdayEnabled.longValue(),
            )
        )
    }

}

private fun Boolean.longValue(): Long {
    return if (this) 1 else 0
}

private fun Long.booleanValue(): Boolean {
    return this == 1L
}
