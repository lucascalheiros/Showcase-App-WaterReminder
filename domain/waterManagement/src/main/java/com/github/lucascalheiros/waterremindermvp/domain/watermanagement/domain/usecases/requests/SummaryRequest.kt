package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.requests

import kotlinx.datetime.LocalDate

sealed interface SummaryRequest {
    data class SingleDay(val date: LocalDate) : SummaryRequest
    data class LastSummaries(val itemsToInclude: Int) : SummaryRequest
}