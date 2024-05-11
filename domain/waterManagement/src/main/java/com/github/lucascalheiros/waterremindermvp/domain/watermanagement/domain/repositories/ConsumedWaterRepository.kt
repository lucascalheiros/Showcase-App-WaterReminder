package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.repositories

import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.ConsumedWater
import kotlinx.coroutines.flow.Flow

internal interface ConsumedWaterRepository {
    fun allFlow(): Flow<List<ConsumedWater>>
    fun allByPeriodFlow(startTimestamp: Long, endTimestamp: Long): Flow<List<ConsumedWater>>
    suspend fun all(): List<ConsumedWater>
    suspend fun allByPeriod(startTimestamp: Long, endTimestamp: Long): List<ConsumedWater>
    suspend fun getById(id: Long): ConsumedWater?
    suspend fun deleteById(id: Long)
    suspend fun deleteAll()
    suspend fun save(data: ConsumedWater)
}