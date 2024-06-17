package com.github.lucascalheiros.waterreminder.measuresystem.domain.repositories

import kotlinx.coroutines.flow.Flow
import java.io.Serializable

interface BaseUnitRepository<T> {
    suspend fun getUnit(): T
    fun getUnitFlow(): Flow<T>
    suspend fun setUnit(unit: T)
}