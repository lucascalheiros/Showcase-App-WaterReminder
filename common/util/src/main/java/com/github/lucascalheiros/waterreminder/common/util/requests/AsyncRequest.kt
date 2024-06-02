package com.github.lucascalheiros.waterreminder.common.util.requests

sealed interface AsyncRequest {
    data object Single: AsyncRequest
    data object Continuous: AsyncRequest
}