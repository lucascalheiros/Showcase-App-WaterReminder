package com.github.lucascalheiros.waterreminder.data.waterdataprovider.datasources.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.models.ConsumedWaterDb
import kotlinx.coroutines.flow.Flow

@Dao
interface ConsumedWaterDao {
    @Query("SELECT * FROM ConsumedWaterDb")
    fun allFlow(): Flow<List<ConsumedWaterDb>>
    @Query("SELECT * FROM ConsumedWaterDb WHERE :startTimestamp <= consumptionTime AND :endTimestamp >= consumptionTime")
    fun allByPeriodFlow(startTimestamp: Long, endTimestamp: Long): Flow<List<ConsumedWaterDb>>
    @Query("SELECT * FROM ConsumedWaterDb")
    suspend fun all(): List<ConsumedWaterDb>
    @Query("SELECT * FROM ConsumedWaterDb WHERE :startTimestamp <= consumptionTime AND :endTimestamp >= consumptionTime")
    suspend fun allByPeriod(startTimestamp: Long, endTimestamp: Long): List<ConsumedWaterDb>
    @Query("SELECT * FROM ConsumedWaterDb WHERE :id = consumedWaterId LIMIT 1")
    suspend fun getById(id: Long): ConsumedWaterDb?
    @Query("DELETE FROM ConsumedWaterDb WHERE :id = consumedWaterId")
    suspend fun deleteById(id: Long)
    @Query("DELETE FROM WaterSourceDb")
    suspend fun deleteAll()
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(data: ConsumedWaterDb)
}