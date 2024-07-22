package com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDay
import com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.notificationtime.NotificationTimeSection

class ManageNotificationsContract {
    interface View {
        fun updateSectionsData(data: ManageNotificationSectionsData)
        fun showNotificationWeekDaysPicker(request: NotificationWeekDaysRequest)
        fun setSelectionModeUI(isSelectionModeEnabled: Boolean)
        fun setOptionCheckUncheckAllOption(isAllChecked: Boolean)
    }

    interface Presenter {
        fun onRemoveScheduleClick(dayTime: DayTime)
        fun onNotificationDaysClick(dayTime: DayTime)
        fun onNotificationWeekDaysChange(dayTime: DayTime, newWeekDays: List<WeekDay>)
        fun onSelectedNotificationWeekDaysChange(newWeekDays: List<WeekDay>)
        fun onItemSelectionToggle(dayTime: DayTime)
        fun onCheckAllClick()
        fun onUncheckAllClick()
        fun onDeleteSelectedClick()
        fun onNotificationDaysSelectedClick()
        fun onCancelSelectionModeClick()
    }
}

data class ManageNotificationSectionsData(
    val notificationTimeSection: List<NotificationTimeSection>
)

sealed interface NotificationWeekDaysRequest {
    val selectedDays: List<WeekDay>

    data class Single(
        val dayTime: DayTime,
        override val selectedDays: List<WeekDay>
    ) : NotificationWeekDaysRequest
    data class Selected(
        override val selectedDays: List<WeekDay>
    ) : NotificationWeekDaysRequest
}
