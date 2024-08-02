package com.github.lucascalheiros.waterreminder.common.util.flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> Flow<T>.launchCollectLatest(
    scope: CoroutineScope,
    action: suspend (value: T) -> Unit
) = scope.launch {
    collectLatest(action)
}
