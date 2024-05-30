package com.github.lucascalheiros.waterremindermvp.common.util.requests

sealed interface AsyncRequest {
    data object Single: AsyncRequest
    data object Continuous: AsyncRequest
}