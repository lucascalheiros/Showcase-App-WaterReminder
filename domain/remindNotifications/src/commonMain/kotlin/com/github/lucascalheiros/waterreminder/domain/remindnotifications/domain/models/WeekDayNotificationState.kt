package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models

data class WeekDayNotificationState(
    val weekDay: WeekDay,
    val isEnabled: Boolean
)