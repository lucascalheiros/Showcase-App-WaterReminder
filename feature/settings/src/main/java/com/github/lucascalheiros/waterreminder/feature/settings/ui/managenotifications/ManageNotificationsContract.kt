package com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDay
import com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.notificationtime.NotificationTimeSection

class ManageNotificationsContract {
    interface View {
        fun updateSectionsData(data: ManageNotificationSectionsData)
        fun showNotificationWeekDaysPicker(dayTime: DayTime, selectedDays: List<WeekDay>)
    }

    interface Presenter {
        fun onRemoveScheduleClick(dayTime: DayTime)
        fun onNotificationDaysClick(dayTime: DayTime)
        fun onNotificationWeekDaysChange(dayTime: DayTime, newWeekDays: List<WeekDay>)
    }
}

data class ManageNotificationSectionsData(
    val notificationTimeSection: List<NotificationTimeSection>
)
