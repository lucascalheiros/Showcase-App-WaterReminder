package com.github.lucascalheiros.waterreminder.feature.history.ui.history.utils

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import java.time.YearMonth

fun YearMonth.datesFromMonth(): List<LocalDate> {
    return (1..lengthOfMonth()).map { LocalDate(year, month, it) }
}

fun LocalDate.datesFromWeek(): List<LocalDate> {
    val daysToRemove = dayOfWeek.value  % 7
    val startDayOfDateWeek = minus(daysToRemove, DateTimeUnit.DAY)
    return (0..6).map { startDayOfDateWeek.plus(it, DateTimeUnit.DAY) }
}
