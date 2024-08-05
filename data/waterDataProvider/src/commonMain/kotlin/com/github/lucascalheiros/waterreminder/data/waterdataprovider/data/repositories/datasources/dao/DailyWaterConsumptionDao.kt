package com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao

import com.github.lucascalheiros.waterreminder.measuresystem.domain.models.MeasureSystemVolume
import com.github.lucascalheiros.waterreminderkmp.data.waterdataprovider.DailyWaterConsumptionDb
import kotlinx.coroutines.flow.Flow

interface DailyWaterConsumptionDao {
    fun allFlow(): Flow<List<DailyWaterConsumptionDb>>
    fun allByPeriodFlow(startTimestamp: Long, endTimestamp: Long): Flow<List<DailyWaterConsumptionDb>>
    suspend fun all(): List<DailyWaterConsumptionDb>
    suspend fun allByPeriod(startTimestamp: Long, endTimestamp: Long): List<DailyWaterConsumptionDb>
    suspend fun getById(id: Long): DailyWaterConsumptionDb?
    suspend fun deleteById(id: Long)
    suspend fun deleteAll()
    suspend fun save(volume: MeasureSystemVolume)
}