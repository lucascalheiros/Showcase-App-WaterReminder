package com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.model

import kotlinx.datetime.LocalTime

data class FirstAccessNotificationData(
    val startTime: LocalTime,
    val stopTime: LocalTime,
    val frequencyTime: LocalTime,
    val shouldEnable: Boolean
)