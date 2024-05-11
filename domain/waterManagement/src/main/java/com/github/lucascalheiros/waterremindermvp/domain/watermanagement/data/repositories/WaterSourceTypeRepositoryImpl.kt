package com.github.lucascalheiros.waterremindermvp.domain.watermanagement.data.repositories

import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterremindermvp.domain.watermanagement.domain.repositories.WaterSourceTypeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal class WaterSourceTypeRepositoryImpl: WaterSourceTypeRepository {
    private val data = MutableStateFlow<List<WaterSourceType>>(listOf())
    override fun allFlow(): Flow<List<WaterSourceType>> {
        return data
    }

    override suspend fun all(): List<WaterSourceType> {
        return data.value
    }

    override suspend fun getById(id: Long): WaterSourceType? {
        return data.value.find { it.waterSourceTypeId == id }
    }

    override suspend fun deleteById(id: Long) {
        data.update {
            it.toMutableList().apply {
                removeIf { it.waterSourceTypeId == id }
            }
        }
    }

    override suspend fun deleteAll() {
        data.update {
            listOf()
        }
    }

    override suspend fun save(data: WaterSourceType) {
        this.data.update {
            it.toMutableList().apply {
                add(data)
            }
        }
    }
}