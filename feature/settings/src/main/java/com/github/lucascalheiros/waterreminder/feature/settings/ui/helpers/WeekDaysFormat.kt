package com.github.lucascalheiros.waterreminder.feature.settings.ui.helpers

import android.content.Context
import com.github.lucascalheiros.waterreminder.domain.remindnotifications.domain.models.WeekDay
import com.github.lucascalheiros.waterreminder.feature.settings.R

fun List<WeekDay>.formatWeekDaysDisplayText(context: Context): String {
    return when {
        size == 7 -> {
            context.getString(R.string.notification_days_every_day)
        }

        isEmpty() -> {
            context.getString(R.string.notification_days_no_days)
        }

        size == 2 && containsAll(listOf(WeekDay.Sunday, WeekDay.Saturday)) -> {
            context.getString(R.string.notification_days_weekend)
        }

        size == 5 && containsAll(
            listOf(
                WeekDay.Monday,
                WeekDay.Tuesday,
                WeekDay.Wednesday,
                WeekDay.Thursday,
                WeekDay.Friday,
            )
        ) -> {
            context.getString(R.string.notification_days_weekdays)
        }

        else -> {
            joinToString(", ") { it.shortDisplay(context) }
        }
    }
}

private fun WeekDay.shortStringRes(): Int {
    return when (this) {
        WeekDay.Sunday -> R.string.short_weekday_sunday
        WeekDay.Monday -> R.string.short_weekday_monday
        WeekDay.Tuesday -> R.string.short_weekday_tuesday
        WeekDay.Wednesday -> R.string.short_weekday_wednesday
        WeekDay.Thursday -> R.string.short_weekday_thursday
        WeekDay.Friday -> R.string.short_weekday_friday
        WeekDay.Saturday -> R.string.short_weekday_saturday
    }
}

private fun WeekDay.shortDisplay(context: Context): String {
    return context.getString(shortStringRes())
}