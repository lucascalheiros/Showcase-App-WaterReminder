package com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories

import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.coverters.toWaterSourceType
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.coverters.toWaterSourceTypeDb
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.WaterSourceTypeDao
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSourceType
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.WaterSourceTypeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class WaterSourceTypeRepositoryImpl(
    private val waterSourceTypeDao: WaterSourceTypeDao,
) : WaterSourceTypeRepository {

    override fun allFlow(): Flow<List<WaterSourceType>> {
        return waterSourceTypeDao.allFlow().map { it.map { it.toWaterSourceType() } }
    }

    override suspend fun all(): List<WaterSourceType> {
        return waterSourceTypeDao.all().map { it.toWaterSourceType() }
    }

    override suspend fun getById(id: Long): WaterSourceType? {
        return waterSourceTypeDao.getById(id)?.toWaterSourceType()
    }

    override suspend fun deleteById(id: Long) {
        waterSourceTypeDao.deleteById(id)
    }

    override suspend fun deleteAll() {
        waterSourceTypeDao.deleteAll()
    }

    override suspend fun save(data: WaterSourceType) {
        waterSourceTypeDao.save(data.toWaterSourceTypeDb())
    }
    
}