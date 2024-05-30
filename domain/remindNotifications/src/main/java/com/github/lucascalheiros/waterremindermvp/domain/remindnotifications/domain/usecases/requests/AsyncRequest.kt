package com.github.lucascalheiros.waterremindermvp.domain.remindnotifications.domain.usecases.requests

sealed interface AsyncRequest {
    data object Single: AsyncRequest
    data object Continuous: AsyncRequest
}