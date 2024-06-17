package com.github.lucascalheiros.waterreminder.data.firstaccessdataprovider.models

import kotlinx.datetime.LocalTime


data class FirstAccessNotificationDataDb(
    val startTime: LocalTime,
    val stopTime: LocalTime,
    val frequency: LocalTime,
    val enabled: Boolean,
)