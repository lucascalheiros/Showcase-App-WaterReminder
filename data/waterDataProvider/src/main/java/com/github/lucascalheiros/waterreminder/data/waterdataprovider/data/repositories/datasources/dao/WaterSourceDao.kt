package com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.models.WaterSourceDb
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterSourceDao {
    @Query("SELECT * FROM WaterSourceDb")
    fun allFlow(): Flow<List<WaterSourceDb>>
    @Query("SELECT * FROM WaterSourceDb")
    suspend fun all(): List<WaterSourceDb>
    @Query("SELECT * FROM WaterSourceDb WHERE :id = waterSourceId LIMIT 1")
    suspend fun getById(id: Long): WaterSourceDb?
    @Query("DELETE FROM WaterSourceDb WHERE :id = waterSourceId")
    suspend fun deleteById(id: Long)
    @Query("DELETE FROM WaterSourceDb")
    suspend fun deleteAll()
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(waterSourceDb: WaterSourceDb)
}