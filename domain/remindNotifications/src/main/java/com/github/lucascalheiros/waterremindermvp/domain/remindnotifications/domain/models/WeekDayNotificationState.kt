package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models

data class WeekDayNotificationState(
    val weekDay: WeekDay,
    val isEnabled: Boolean
)