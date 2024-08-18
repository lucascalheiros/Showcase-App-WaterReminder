package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests

import com.github.lucascalheiros.waterreminder.common.util.date.TimeInterval
import kotlinx.datetime.LocalDate

sealed interface SummaryRequest {
    data class SingleDay(val date: LocalDate) : SummaryRequest
    data class Interval(val interval: TimeInterval) : SummaryRequest
    data class LastSummaries(val itemsToInclude: Int) : SummaryRequest
}