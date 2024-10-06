package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models

data class NotificationsPeriod(
    val start: DayTime,
    val stop: DayTime,
    val period: DayTime
)