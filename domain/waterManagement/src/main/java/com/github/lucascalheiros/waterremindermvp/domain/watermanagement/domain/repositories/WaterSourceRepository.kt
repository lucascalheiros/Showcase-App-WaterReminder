package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.repositories

import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.WaterSource
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.usecases.requests.CreateWaterSourceRequest
import kotlinx.coroutines.flow.Flow

internal interface WaterSourceRepository {
    fun allFlow(): Flow<List<WaterSource>>
    suspend fun all(): List<WaterSource>
    suspend fun getById(id: Long): WaterSource?
    suspend fun deleteById(id: Long)
    suspend fun deleteAll()
    suspend fun create(request: CreateWaterSourceRequest)
}