package com.github.lucascalheiros.watertreminder.domain.firstaccess.domain.repositories

interface FirstUseFlagsRepository {
    suspend fun isDailyIntakeFirstSet(): Boolean
    suspend fun isFirstAccessCompleted(): Boolean
    suspend fun setDailyIntakeSetFlag(state: Boolean)
    suspend fun setFirstAccessCompletedFlag(state: Boolean)
}