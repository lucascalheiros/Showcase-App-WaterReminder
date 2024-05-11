package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.requests

import com.github.lucascalheiros.waterremindermvp.common.util.date.TimeInterval

sealed interface ConsumedWaterRequest {
    data class FromTimeInterval(val interval: TimeInterval) : ConsumedWaterRequest
    data class ItemsFromLastDays(val daysToInclude: Int) : ConsumedWaterRequest
}