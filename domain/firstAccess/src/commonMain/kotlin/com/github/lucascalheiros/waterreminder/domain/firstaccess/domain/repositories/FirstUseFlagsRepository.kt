package com.github.lucascalheiros.waterreminder.domain.firstaccess.domain.repositories

import kotlinx.coroutines.flow.Flow

interface FirstUseFlagsRepository {
    suspend fun isDailyIntakeFirstSet(): Boolean
    suspend fun isFirstAccessCompleted(): Boolean
    fun isFirstAccessCompletedFlow(): Flow<Boolean>
    suspend fun setDailyIntakeSetFlag(state: Boolean)
    suspend fun setFirstAccessCompletedFlag(state: Boolean)
}