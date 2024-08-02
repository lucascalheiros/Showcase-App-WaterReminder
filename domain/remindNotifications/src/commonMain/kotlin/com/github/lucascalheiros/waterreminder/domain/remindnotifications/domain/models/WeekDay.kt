package com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models

enum class WeekDay(val dayNumber: Int) {
    Sunday(0),
    Monday(1),
    Tuesday(2),
    Wednesday(3),
    Thursday(4),
    Friday(5),
    Saturday(6);

    companion object {
        fun fromDayNumber(dayNumber: Int): WeekDay? {
            return entries.find { it.dayNumber == dayNumber }
        }
    }
}