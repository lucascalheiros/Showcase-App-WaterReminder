package com.github.lucascalheiros.waterreminder.data.waterdataprovider.datasources.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.models.WaterSourceTypeDb
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterSourceTypeDao {
    @Query("SELECT * FROM WaterSourceTypeDb")
    fun allFlow(): Flow<List<WaterSourceTypeDb>>
    @Query("SELECT * FROM WaterSourceTypeDb")
    suspend fun all(): List<WaterSourceTypeDb>
    @Query("SELECT * FROM WaterSourceTypeDb WHERE :id = waterSourceTypeId LIMIT 1")
    suspend fun getById(id: Long): WaterSourceTypeDb?
    @Query("DELETE FROM WaterSourceTypeDb WHERE :id = waterSourceTypeId")
    suspend fun deleteById(id: Long)
    @Query("DELETE FROM WaterSourceDb")
    suspend fun deleteAll()
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(data: WaterSourceTypeDb)
}