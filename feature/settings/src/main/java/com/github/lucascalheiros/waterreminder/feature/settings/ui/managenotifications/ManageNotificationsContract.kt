package com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDay
import com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.notificationtime.NotificationTimeSection
import com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.weekdaysswitch.WeekDaySwitchSection

class ManageNotificationsContract {
    interface View {
        fun updateSectionsData(data: ManageNotificationSectionsData)
    }

    interface Presenter {
        fun onAddSchedule(dayTime: DayTime)
        fun onRemoveScheduleClick(dayTime: DayTime)
        fun onWeekDayNotificationStateChange(weekDay: WeekDay, state: Boolean)
    }
}

data class ManageNotificationSectionsData(
    val weekDaySection: List<WeekDaySwitchSection>,
    val notificationTimeSection: List<NotificationTimeSection>
)