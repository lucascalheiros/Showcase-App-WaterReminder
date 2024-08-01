package com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao

import com.github.lucascalheiros.waterreminderkmp.data.waterdataprovider.WaterSourceTypeDb
import kotlinx.coroutines.flow.Flow

interface WaterSourceTypeDao {
    fun allFlow(): Flow<List<WaterSourceTypeDb>>
    suspend fun all(): List<WaterSourceTypeDb>
    suspend fun getById(id: Long): WaterSourceTypeDb?
    suspend fun deleteById(id: Long)
    suspend fun deleteAll()
    suspend fun save(data: WaterSourceTypeDb)
}