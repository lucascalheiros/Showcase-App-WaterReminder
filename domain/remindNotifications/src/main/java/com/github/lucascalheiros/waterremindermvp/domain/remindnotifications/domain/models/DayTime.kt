package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.models

data class DayTime(
    val hour: Int,
    val minute: Int
) {
    val dayMinutes = hour * 60 + minute
    val daySeconds = dayMinutes * 60

    companion object {
        fun fromDayMinutes(dayMinutes: Int): DayTime {
            return DayTime(dayMinutes / 60, dayMinutes % 60)
        }
    }
}
