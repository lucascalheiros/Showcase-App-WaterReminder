package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models

data class WeekState(
    val sundayEnabled: Boolean,
    val mondayEnabled: Boolean,
    val tuesdayEnabled: Boolean,
    val wednesdayEnabled: Boolean,
    val thursdayEnabled: Boolean,
    val fridayEnabled: Boolean,
    val saturdayEnabled: Boolean
)