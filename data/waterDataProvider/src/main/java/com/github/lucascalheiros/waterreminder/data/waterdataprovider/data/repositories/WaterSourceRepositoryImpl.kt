package com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories

import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.repositories.datasources.dao.WaterSourceDao
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.coverters.toWaterSource
import com.github.lucascalheiros.waterreminder.data.waterdataprovider.data.coverters.toWaterSourceDb
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.models.WaterSource
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.repositories.WaterSourceRepository
import com.github.lucascalheiros.waterreminder.domain.watermanagement.domain.usecases.requests.CreateWaterSourceRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class WaterSourceRepositoryImpl(
    private val waterSourceDao: WaterSourceDao
) : WaterSourceRepository {
    override fun allFlow(): Flow<List<WaterSource>> {
        return waterSourceDao.allFlow().map { it.map { it.toWaterSource() } }
    }

    override suspend fun all(): List<WaterSource> {
        return waterSourceDao.all().map { it.toWaterSource() }
    }

    override suspend fun getById(id: Long): WaterSource? {
        return waterSourceDao.getById(id)?.toWaterSource()
    }

    override suspend fun deleteById(id: Long) {
        waterSourceDao.deleteById(id)
    }

    override suspend fun deleteAll() {
        waterSourceDao.deleteAll()
    }

    override suspend fun create(request: CreateWaterSourceRequest) {
        waterSourceDao.save(WaterSource(-1, request.volume, request.waterSourceType).toWaterSourceDb())
    }

}