package com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao

import com.github.lucascalheiros.waterreminderkmp.data.waterdataprovider.ConsumedWaterDb
import kotlinx.coroutines.flow.Flow

interface ConsumedWaterDao {
    fun allFlow(): Flow<List<ConsumedWaterDb>>
    fun allByPeriodFlow(startTimestamp: Long, endTimestamp: Long): Flow<List<ConsumedWaterDb>>
    suspend fun all(): List<ConsumedWaterDb>
    suspend fun allByPeriod(startTimestamp: Long, endTimestamp: Long): List<ConsumedWaterDb>
    suspend fun getById(id: Long): ConsumedWaterDb?
    suspend fun deleteById(id: Long)
    suspend fun deleteAll()
    suspend fun save(data: ConsumedWaterDb)
}