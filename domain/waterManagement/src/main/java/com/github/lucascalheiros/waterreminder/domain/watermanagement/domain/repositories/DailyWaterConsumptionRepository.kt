package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.DailyWaterConsumption
import kotlinx.coroutines.flow.Flow

interface DailyWaterConsumptionRepository {
    fun allFlow(): Flow<List<DailyWaterConsumption>>
    fun allByPeriodFlow(startTimestamp: Long, endTimestamp: Long): Flow<List<DailyWaterConsumption>>
    suspend fun all(): List<DailyWaterConsumption>
    suspend fun allByPeriod(startTimestamp: Long, endTimestamp: Long): List<DailyWaterConsumption>
    suspend fun getById(id: Long): DailyWaterConsumption?
    suspend fun deleteById(id: Long)
    suspend fun deleteAll()
    suspend fun save(data: DailyWaterConsumption)
}