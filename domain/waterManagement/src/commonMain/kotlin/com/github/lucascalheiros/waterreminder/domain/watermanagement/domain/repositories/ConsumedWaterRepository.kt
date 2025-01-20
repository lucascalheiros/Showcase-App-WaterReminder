package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ConsumedWater
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import kotlinx.coroutines.flow.Flow

interface ConsumedWaterRepository {
    fun allFlow(): Flow<List<ConsumedWater>>
    fun allByPeriodFlow(startTimestamp: Long, endTimestamp: Long): Flow<List<ConsumedWater>>
    suspend fun all(): List<ConsumedWater>
    suspend fun allByPeriod(startTimestamp: Long, endTimestamp: Long): List<ConsumedWater>
    suspend fun getById(id: Long): ConsumedWater?
    suspend fun deleteById(id: Long)
    suspend fun deleteAll()
    suspend fun register(volume: MeasureSystemVolume, waterSourceType: WaterSourceType, timestamp: Long)
}