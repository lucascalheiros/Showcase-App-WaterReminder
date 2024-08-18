package com.github.lucascalheiros.waterreminder.common.util.date

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

fun getLengthOfMonth(year: Int, month: Int): Int {
    return LocalDate(year, month, 1).lengthOfMonth()
}

fun LocalDate.withDayOfMonth(dayOfMonth: Int) = LocalDate(year, month, dayOfMonth)

fun LocalDate.atEndOfMonth(): LocalDate {
    return withDayOfMonth(lengthOfMonth())
}

fun LocalDate.lengthOfMonth(): Int {
    return withDayOfMonth(1)
        .plus(1, DateTimeUnit.MONTH)
        .minus(1, DateTimeUnit.DAY)
        .dayOfMonth
}

fun LocalDate.datesFromWeek(): List<LocalDate> {
    val daysToRemove = (dayOfWeek.ordinal + 1) % 7
    val startDayOfDateWeek = minus(daysToRemove, DateTimeUnit.DAY)
    return (0..6).map { startDayOfDateWeek.plus(it, DateTimeUnit.DAY) }
}

fun LocalDate.toYearAndMonth(): YearAndMonth {
    return YearAndMonth(year, monthNumber)
}