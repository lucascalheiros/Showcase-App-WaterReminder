package com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.mock

import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.ConsumedWaterDao
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ConsumedWater
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.ConsumedWaterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

internal class ConsumedWaterRepositoryImpl(
    private val consumedWaterDao: ConsumedWaterDao
): ConsumedWaterRepository {
    private val data = MutableStateFlow<List<ConsumedWater>>(listOf())
    override fun allFlow(): Flow<List<ConsumedWater>> {
        return data
    }

    override suspend fun all(): List<ConsumedWater> {
        return data.value
    }

    override suspend fun getById(id: Long): ConsumedWater? {
        return data.value.find { it.consumedWaterId == id }
    }

    override suspend fun deleteById(id: Long) {
        data.update {
            it.toMutableList().apply {
                removeIf { it.consumedWaterId == id }
            }
        }
    }

    override suspend fun deleteAll() {
        data.update {
            listOf()
        }
    }

    override suspend fun save(data: ConsumedWater) {
        this.data.update {
            it.toMutableList().apply {
                add(data.copy(consumedWaterId = it.size.toLong()))
            }
        }
    }
    override fun allByPeriodFlow(
        startTimestamp: Long,
        endTimestamp: Long
    ): Flow<List<ConsumedWater>> {
        return data.map { list ->
            list.filter { it.consumptionTime in startTimestamp..endTimestamp }
        }
    }

    override suspend fun allByPeriod(
        startTimestamp: Long,
        endTimestamp: Long
    ): List<ConsumedWater> {
        return data.value.filter { it.consumptionTime in startTimestamp..endTimestamp }
    }
}