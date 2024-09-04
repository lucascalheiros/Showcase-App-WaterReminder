package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models

data class WeekState(
    val sundayEnabled: Boolean,
    val mondayEnabled: Boolean,
    val tuesdayEnabled: Boolean,
    val wednesdayEnabled: Boolean,
    val thursdayEnabled: Boolean,
    val fridayEnabled: Boolean,
    val saturdayEnabled: Boolean
) {
    fun enabledWeekDays(): List<WeekDay> {
        return buildList {
            sundayEnabled && add(WeekDay.Sunday)
            mondayEnabled && add(WeekDay.Monday)
            tuesdayEnabled && add(WeekDay.Tuesday)
            wednesdayEnabled && add(WeekDay.Wednesday)
            thursdayEnabled && add(WeekDay.Thursday)
            fridayEnabled && add(WeekDay.Friday)
            saturdayEnabled && add(WeekDay.Saturday)
        }
    }
}