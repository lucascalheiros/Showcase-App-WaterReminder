package com.github.lucascalheiros.waterremindermvp.feature.settings.ui.managenotifications.adapters.notificationtime

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.DayTime

sealed interface NotificationTimeSection {
    data object Title : NotificationTimeSection
    sealed interface Content : NotificationTimeSection {
        data class Item(val dayTime: DayTime) : Content
        data object AddItem : Content
    }
}