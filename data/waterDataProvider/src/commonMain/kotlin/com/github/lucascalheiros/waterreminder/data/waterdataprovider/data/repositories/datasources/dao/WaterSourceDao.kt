package com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao

import com.github.lucascalheiros.waterreminderkmp.data.waterdataprovider.WaterSourceDb
import kotlinx.coroutines.flow.Flow

interface WaterSourceDao {
    fun allFlow(): Flow<List<WaterSourceDb>>
    suspend fun all(): List<WaterSourceDb>
    suspend fun getById(id: Long): WaterSourceDb?
    suspend fun deleteById(id: Long)
    suspend fun deleteAll()
    suspend fun save(data: WaterSourceDb)
}