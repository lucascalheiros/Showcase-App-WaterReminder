package com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.CreateWaterSourceTypeRequest
import kotlinx.coroutines.flow.Flow

interface WaterSourceTypeRepository {
    fun allFlow(): Flow<List<WaterSourceType>>
    suspend fun all(): List<WaterSourceType>
    suspend fun getById(id: Long): WaterSourceType?
    suspend fun deleteById(id: Long)
    suspend fun deleteAll()
    suspend fun create(request: CreateWaterSourceTypeRequest)
}