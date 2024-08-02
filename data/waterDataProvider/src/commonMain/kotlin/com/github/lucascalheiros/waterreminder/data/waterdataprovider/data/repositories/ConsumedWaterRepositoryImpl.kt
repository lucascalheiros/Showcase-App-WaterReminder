package com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories

import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.ConsumedWaterDao
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.coverters.toConsumedWater
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.coverters.toConsumedWaterDb
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.ConsumedWater
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.ConsumedWaterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ConsumedWaterRepositoryImpl(
    private val consumedWaterDao: ConsumedWaterDao
) : ConsumedWaterRepository {

    override fun allFlow(): Flow<List<ConsumedWater>> {
        return consumedWaterDao.allFlow().map { it.map { it.toConsumedWater() } }
    }

    override suspend fun all(): List<ConsumedWater> {
        return consumedWaterDao.all().map { it.toConsumedWater() }
    }

    override suspend fun getById(id: Long): ConsumedWater? {
        return consumedWaterDao.getById(id)?.toConsumedWater()
    }

    override suspend fun deleteById(id: Long) {
        consumedWaterDao.deleteById(id)
    }

    override suspend fun deleteAll() {
        consumedWaterDao.deleteAll()
    }

    override suspend fun save(data: ConsumedWater) {
        consumedWaterDao.save(data.toConsumedWaterDb())
    }

    override fun allByPeriodFlow(
        startTimestamp: Long,
        endTimestamp: Long
    ): Flow<List<ConsumedWater>> {
        return consumedWaterDao.allByPeriodFlow(startTimestamp, endTimestamp).map { it.map { it.toConsumedWater() } }
    }

    override suspend fun allByPeriod(
        startTimestamp: Long,
        endTimestamp: Long
    ): List<ConsumedWater> {
        return consumedWaterDao.allByPeriod(startTimestamp, endTimestamp).map { it.toConsumedWater() }
    }
}