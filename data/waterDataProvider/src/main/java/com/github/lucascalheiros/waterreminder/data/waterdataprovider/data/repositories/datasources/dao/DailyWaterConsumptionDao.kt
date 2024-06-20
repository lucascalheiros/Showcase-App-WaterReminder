package com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.models.DailyWaterConsumptionDb
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyWaterConsumptionDao {
    @Query("SELECT * FROM DailyWaterConsumptionDb")
    fun allFlow(): Flow<List<DailyWaterConsumptionDb>>
    @Query("SELECT * FROM DailyWaterConsumptionDb WHERE :startTimestamp <= date AND :endTimestamp >= date")
    fun allByPeriodFlow(startTimestamp: Long, endTimestamp: Long): Flow<List<DailyWaterConsumptionDb>>
    @Query("SELECT * FROM DailyWaterConsumptionDb")
    suspend fun all(): List<DailyWaterConsumptionDb>
    @Query("SELECT * FROM DailyWaterConsumptionDb WHERE :startTimestamp <= date AND :endTimestamp >= date")
    suspend fun allByPeriod(startTimestamp: Long, endTimestamp: Long): List<DailyWaterConsumptionDb>
    @Query("SELECT * FROM DailyWaterConsumptionDb WHERE :id = dailyWaterConsumptionId LIMIT 1")
    suspend fun getById(id: Long): DailyWaterConsumptionDb?
    @Query("DELETE FROM DailyWaterConsumptionDb WHERE :id = dailyWaterConsumptionId")
    suspend fun deleteById(id: Long)
    @Query("DELETE FROM DailyWaterConsumptionDb")
    suspend fun deleteAll()
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(data: DailyWaterConsumptionDb)
}