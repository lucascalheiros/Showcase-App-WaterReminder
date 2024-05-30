package com.github.lucascalheiros.waterremindermvp.feature.settings.ui.managenotifications.adapters.weekdaysswitch

import com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models.WeekDay

sealed interface WeekDaySwitchSection {
    data object Title: WeekDaySwitchSection
    data class Item(
        val weekDay: WeekDay,
        val enabled: Boolean
    ): WeekDaySwitchSection
}