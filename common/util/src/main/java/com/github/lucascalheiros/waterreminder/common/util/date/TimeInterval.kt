package com.github.lucascalheiros.waterreminder.common.util.date

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

data class TimeInterval(
    val startTimestamp: Long,
    val endTimestamp: Long
)

fun LocalDate.toDayTimeInterval(): TimeInterval {
    return TimeInterval(
        atStartOfDay().toEpochMilliseconds(),
        atEndOfDay().toEpochMilliseconds()
    )
}

fun LocalDate.atStartOfDay(): Instant {
    return atStartOfDayIn(TimeZone.currentSystemDefault())
}

fun LocalDate.atEndOfDay(): Instant {
    return plus(1, DateTimeUnit.DAY)
        .atStartOfDayIn(TimeZone.currentSystemDefault())
        .minus(1, DateTimeUnit.MILLISECOND)
}

fun lastDaysPeriod(days: Long): TimeInterval {
    val localDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    return TimeInterval(
        localDate.minus(days, DateTimeUnit.DAY).atStartOfDayIn(TimeZone.currentSystemDefault())
            .toEpochMilliseconds(),
        localDate.plus(1, DateTimeUnit.DAY).atStartOfDayIn(TimeZone.currentSystemDefault())
            .toEpochMilliseconds()
    )
}

fun Long.epochMillisToLocalDate(): LocalDate {
    return Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date
}

fun todayLocalDate(): LocalDate {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
}
