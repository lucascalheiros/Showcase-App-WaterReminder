package com.github.lucascalheiros.waterreminder.feature.settings.ui.managenotifications.adapters.weekdaysswitch

import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDay

sealed interface WeekDaySwitchSection {
    data object Title: WeekDaySwitchSection
    data class Item(
        val weekDay: WeekDay,
        val enabled: Boolean
    ): WeekDaySwitchSection
}