package com.github.lucascalheiros.waterreminder.domain.history.domain.models

import com.github.lucascalheiros.waterreminder.common.util.date.YearAndMonth
import com.github.lucascalheiros.waterreminder.common.util.date.datesFromWeek
import com.github.lucascalheiros.waterreminder.common.util.date.getLengthOfMonth
import com.github.lucascalheiros.waterreminder.common.util.date.toYearAndMonth
import kotlinx.datetime.LocalDate

sealed interface HistoryChartPeriod {
    val startDate: LocalDate
    val endDate: LocalDate
    val chartPeriodOption: ChartOption

    data class Week(private val baselineDate: LocalDate) : HistoryChartPeriod {
        val dates = baselineDate.datesFromWeek()
        override val startDate by lazy { dates.min() }
        override val endDate by lazy { dates.max() }
        override val chartPeriodOption = ChartOption.Week
    }

    data class Month(private val baselineDate: LocalDate) : HistoryChartPeriod {
        val yearAndMonth by lazy {
            baselineDate.toYearAndMonth()
        }
        val dates = (1..getLengthOfMonth(
            yearAndMonth.year,
            yearAndMonth.month
        )).map { LocalDate(yearAndMonth.year, yearAndMonth.month, it) }
        override val startDate by lazy { dates.min() }
        override val endDate by lazy { dates.max() }
        override val chartPeriodOption = ChartOption.Month
    }

    data class Year(private val baselineDate: LocalDate) : HistoryChartPeriod {
        val year = baselineDate.year
        val months = (1..12).map {
            YearAndMonth(year, it)
        }
        override val startDate by lazy { LocalDate(year, 1, 1) }
        override val endDate by lazy { LocalDate(year, 12, 31) }
        override val chartPeriodOption = ChartOption.Year
    }

    companion object {
        internal fun from(option: ChartOption, baseline: LocalDate): HistoryChartPeriod {
            return when (option) {
                ChartOption.Week -> Week(baseline)
                ChartOption.Month -> Month(baseline)
                ChartOption.Year -> Year(baseline)
            }
        }
    }
}
