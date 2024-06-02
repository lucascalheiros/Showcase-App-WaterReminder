package com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.notificationtime

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.DayTime

sealed interface NotificationTimeSection {
    data object Title : NotificationTimeSection
    sealed interface Content : NotificationTimeSection {
        data class Item(val dayTime: DayTime) : Content
        data object AddItem : Content
    }
}