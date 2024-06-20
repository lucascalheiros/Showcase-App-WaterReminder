package com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.mock

import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSource
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.WaterSourceRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.CreateWaterSourceRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal class WaterSourceRepositoryImpl : WaterSourceRepository {

    private val data = MutableStateFlow<List<WaterSource>>(listOf())
    override fun allFlow(): Flow<List<WaterSource>> {
        return data
    }

    override suspend fun all(): List<WaterSource> {
        return data.value
    }

    override suspend fun getById(id: Long): WaterSource? {
        return data.value.find { it.waterSourceId == id }
    }

    override suspend fun deleteById(id: Long) {
        data.update {
            it.toMutableList().apply {
                removeIf { it.waterSourceId == id }
            }
        }
    }

    override suspend fun deleteAll() {
        data.update {
            listOf()
        }
    }

    override suspend fun create(request: CreateWaterSourceRequest) {
        this.data.update {
            it.toMutableList().apply {
                add(
                    WaterSource(
                        waterSourceId = it.size.toLong(),
                        volume = request.volume,
                        waterSourceType = request.waterSourceType
                    )
                )
            }
        }
    }
}