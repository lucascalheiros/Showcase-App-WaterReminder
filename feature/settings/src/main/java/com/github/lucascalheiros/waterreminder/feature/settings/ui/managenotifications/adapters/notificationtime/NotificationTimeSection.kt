package com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.notificationtime

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDay

sealed interface NotificationTimeSection {
    data object Title : NotificationTimeSection
    sealed interface Content : NotificationTimeSection {
        data class Item(
            val dayTime: DayTime,
            val weekdaysState: List<WeekdayState>,
            val selectionMode: Boolean,
            val isSelected: Boolean,
        ) : Content
        data object AddItem : Content
    }
}
data class WeekdayState(
    val weekDay: WeekDay,
    val enabled: Boolean
)